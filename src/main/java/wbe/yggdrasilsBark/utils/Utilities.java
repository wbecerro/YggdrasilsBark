package wbe.yggdrasilsBark.utils;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.MobExecutor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.yggdrasilsBark.YggdrasilsBark;
import wbe.yggdrasilsBark.rarities.Rarity;
import wbe.yggdrasilsBark.rarities.Reward;
import wbe.yggdrasilsBark.rarities.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utilities {

    private YggdrasilsBark plugin = YggdrasilsBark.getInstance();

    public ItemStack changeAxeMode(ItemStack axe, boolean mode) {
        int modeLine = findLine(axe, YggdrasilsBark.config.axeMode.split(":")[0]);

        ItemMeta meta = axe.getItemMeta();
        List<String> lore = meta.getLore();

        String newMode;
        if(mode) {
            newMode = YggdrasilsBark.config.activeMode;
        } else {
            newMode = YggdrasilsBark.config.inactiveMode;
        }

        lore.set(modeLine, YggdrasilsBark.config.axeMode.replace("%mode%", newMode));
        meta.setLore(lore);
        axe.setItemMeta(meta);

        return axe;
    }

    public int getPlayerCreatureChance(Player player) {
        int chance = YggdrasilsBark.config.baseCreatureChance;

        PlayerInventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();
        ItemStack offHand = inventory.getItemInOffHand();
        ItemStack[] armor = inventory.getArmorContents();

        if(!mainHand.getType().equals(Material.AIR)) {
            chance += getItemCreatureChance(mainHand);
        }

        if(!offHand.getType().equals(Material.AIR)) {
            chance += getItemCreatureChance(offHand);
        }

        for(ItemStack item : armor) {
            if(item == null) {
                continue;
            }
            chance += getItemCreatureChance(item);
        }

        return chance;
    }

    private int getItemCreatureChance(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return 0;
        }

        NamespacedKey modeKey = new NamespacedKey(plugin, "AxeMode");
        if(meta.getPersistentDataContainer().has(modeKey)) {
            if(!meta.getPersistentDataContainer().get(modeKey, PersistentDataType.BOOLEAN)) {
                return 0;
            }
        }

        NamespacedKey baseCreatureKey = new NamespacedKey(plugin, "creatureChance");
        if(meta.getPersistentDataContainer().has(baseCreatureKey)) {
            return meta.getPersistentDataContainer().get(baseCreatureKey, PersistentDataType.INTEGER);
        }

        return 0;
    }

    public int getPlayerItemChance(Player player) {
        int chance = YggdrasilsBark.config.baseItemChance;

        PlayerInventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();
        ItemStack offHand = inventory.getItemInOffHand();
        ItemStack[] armor = inventory.getArmorContents();

        if(!mainHand.getType().equals(Material.AIR)) {
            chance += getItemItemChance(mainHand);
        }

        if(!offHand.getType().equals(Material.AIR)) {
            chance += getItemItemChance(offHand);
        }

        for(ItemStack item : armor) {
            if(item == null) {
                continue;
            }
            chance += getItemItemChance(item);
        }

        return chance;
    }

    private int getItemItemChance(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return 0;
        }

        NamespacedKey modeKey = new NamespacedKey(plugin, "AxeMode");
        if(meta.getPersistentDataContainer().has(modeKey)) {
            if(!meta.getPersistentDataContainer().get(modeKey, PersistentDataType.BOOLEAN)) {
                return 0;
            }
        }

        NamespacedKey baseItemKey = new NamespacedKey(plugin, "itemChance");
        if(meta.getPersistentDataContainer().has(baseItemKey)) {
            return meta.getPersistentDataContainer().get(baseItemKey, PersistentDataType.INTEGER);
        }

        return 0;
    }

    public int getPlayerDoubleChance(Player player) {
        int chance = 0;

        PlayerInventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();
        ItemStack offHand = inventory.getItemInOffHand();
        ItemStack[] armor = inventory.getArmorContents();

        if(!mainHand.getType().equals(Material.AIR)) {
            chance += getItemDoubleChance(mainHand);
        }

        if(!offHand.getType().equals(Material.AIR)) {
            chance += getItemDoubleChance(offHand);
        }

        for(ItemStack item : armor) {
            if(item == null) {
                continue;
            }
            chance += getItemDoubleChance(item);
        }

        return chance;
    }

    private int getItemDoubleChance(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return 0;
        }

        NamespacedKey modeKey = new NamespacedKey(plugin, "AxeMode");
        if(meta.getPersistentDataContainer().has(modeKey)) {
            if(!meta.getPersistentDataContainer().get(modeKey, PersistentDataType.BOOLEAN)) {
                return 0;
            }
        }

        NamespacedKey baseDoubleKey = new NamespacedKey(plugin, "doubleChance");
        if(meta.getPersistentDataContainer().has(baseDoubleKey)) {
            return meta.getPersistentDataContainer().get(baseDoubleKey, PersistentDataType.INTEGER);
        }

        return 0;
    }

    public Rarity calculateRarity() {
        Random random = new Random();
        int randomNumber = random.nextInt(YggdrasilsBark.config.totalRarityWeight);
        int weight = 0;
        List<Rarity> rarities = YggdrasilsBark.config.rarities;

        for(Rarity rarity : rarities) {
            weight += rarity.getWeight();
            if(randomNumber < weight) {
                return rarity;
            }
        }

        return rarities.get(rarities.size() - 1);
    }

    public void spawnCreature(Block block, Tree tree, Player player) {
        Rarity rarity = calculateRarity();
        String mob = getRandomCreature(tree);
        Location location = block.getLocation();
        MobExecutor mobExecutor = MythicBukkit.inst().getMobManager();
        MythicMob mythicMob = mobExecutor.getMythicMob(mob).get();
        mobExecutor.spawnMob(mob, location);
        String message = rarity.getPrefix() + tree.getMessage().replace("%creature%", mythicMob.getDisplayName().get());
        player.sendMessage(message);
    }

    private String getRandomCreature(Tree tree) {
        Random random = new Random();
        return tree.getCreatures().get(random.nextInt(tree.getCreatures().size()));
    }

    public void giveReward(Tree tree, Player player) {
        Rarity rarity = calculateRarity();
        Reward reward = getRandomReward(rarity);
        String command = reward.getCommand().replace("%player%", player.getName());
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        String message = rarity.getPrefix() + reward.getSuffix();
        player.sendMessage(message);
    }

    private Reward getRandomReward(Rarity rarity) {
        List<Reward> rewards = rarity.getRewards();
        Random random = new Random();

        return rewards.get(random.nextInt(rewards.size()));
    }

    public void addDoubleDropChance(ItemStack item, int chance) {
        NamespacedKey baseDoubleKey = new NamespacedKey(plugin, "doubleChance");
        String loreLine = YggdrasilsBark.config.axeDoubleChance
                .replace("%double_chance%", String.valueOf(chance));
        ItemMeta meta = item.getItemMeta();

        if(meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }

        List<String> lore = new ArrayList<>();
        if(meta.hasLore()) {
            lore = meta.getLore();
        }

        lore.add(loreLine);
        meta.setLore(lore);

        meta.getPersistentDataContainer().set(baseDoubleKey, PersistentDataType.INTEGER, chance);
        item.setItemMeta(meta);
    }

    public void addItemChance(ItemStack item, int chance) {
        NamespacedKey baseItemKey = new NamespacedKey(plugin, "itemChance");
        String loreLine = YggdrasilsBark.config.axeItemChance
                .replace("%item_chance%", String.valueOf(chance));
        ItemMeta meta = item.getItemMeta();

        if(meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }

        List<String> lore = new ArrayList<>();
        if(meta.hasLore()) {
            lore = meta.getLore();
        }

        lore.add(loreLine);
        meta.setLore(lore);

        meta.getPersistentDataContainer().set(baseItemKey, PersistentDataType.INTEGER, chance);
        item.setItemMeta(meta);
    }

    public void addCreatureChance(ItemStack item, int chance) {
        NamespacedKey baseCreatureKey = new NamespacedKey(plugin, "creatureChance");
        String loreLine = YggdrasilsBark.config.axeCreatureChance
                .replace("%creature_chance%", String.valueOf(chance));
        ItemMeta meta = item.getItemMeta();

        if(meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }

        List<String> lore = new ArrayList<>();
        if(meta.hasLore()) {
            lore = meta.getLore();
        }

        lore.add(loreLine);
        meta.setLore(lore);

        meta.getPersistentDataContainer().set(baseCreatureKey, PersistentDataType.INTEGER, chance);
        item.setItemMeta(meta);
    }

    private int findLine(ItemStack item, String line) {
        List<String> lore = item.getItemMeta().getLore();
        int size = lore.size();
        for(int i=0;i<size;i++) {
            if(lore.get(i).contains(line)) {
                return i;
            }
        }

        return -1;
    }
}
