package wbe.yggdrasilsBark.utils;

import com.gmail.nossr50.util.player.UserManager;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.MobExecutor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.yggdrasilsBark.YggdrasilsBark;
import wbe.yggdrasilsBark.events.PlayerReceiveRewardEvent;
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

    public double getPlayerCreatureChance(Player player) {
        double chance = YggdrasilsBark.config.baseCreatureChance;

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

        NamespacedKey baseCreatureKey = new NamespacedKey(plugin, "creatureChance");
        if(player.getPersistentDataContainer().has(baseCreatureKey)) {
            chance += player.getPersistentDataContainer().get(baseCreatureKey, PersistentDataType.DOUBLE);
        }

        return chance;
    }

    private double getItemCreatureChance(ItemStack item) {
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
            return meta.getPersistentDataContainer().get(baseCreatureKey, PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public double getPlayerItemChance(Player player) {
        double chance = YggdrasilsBark.config.baseItemChance;

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

        NamespacedKey baseItemKey = new NamespacedKey(plugin, "itemChance");
        if(player.getPersistentDataContainer().has(baseItemKey)) {
            chance += player.getPersistentDataContainer().get(baseItemKey, PersistentDataType.DOUBLE);
        }

        return chance;
    }

    private double getItemItemChance(ItemStack item) {
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
            return meta.getPersistentDataContainer().get(baseItemKey, PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public double getPlayerDoubleChance(Player player) {
        double chance = YggdrasilsBark.config.baseDoubleDropChance;

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

        NamespacedKey baseDoubleKey = new NamespacedKey(plugin, "doubleChance");
        if(player.getPersistentDataContainer().has(baseDoubleKey)) {
            chance += player.getPersistentDataContainer().get(baseDoubleKey, PersistentDataType.DOUBLE);
        }

        return chance;
    }

    private double getItemDoubleChance(ItemStack item) {
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
            return meta.getPersistentDataContainer().get(baseDoubleKey, PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public double getPlayerBoostedChance(Rarity rarity, Player player) {
        double chance = 0;

        PlayerInventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();
        ItemStack offHand = inventory.getItemInOffHand();
        ItemStack[] armor = inventory.getArmorContents();

        if(!mainHand.getType().equals(Material.AIR)) {
            chance += getItemBoostedChance(rarity, mainHand);
        }

        if(!offHand.getType().equals(Material.AIR)) {
            chance += getItemBoostedChance(rarity, offHand);
        }

        for(ItemStack item : armor) {
            if(item == null) {
                continue;
            }
            chance += getItemBoostedChance(rarity, item);
        }

        NamespacedKey rarityKey = new NamespacedKey(plugin, "boostRarity");
        if(player.getPersistentDataContainer().has(rarityKey)) {
            if(player.getPersistentDataContainer().get(rarityKey, PersistentDataType.STRING).equalsIgnoreCase(rarity.getInternalName())) {
                NamespacedKey percentKey = new NamespacedKey(plugin, "boostRarityPercent");
                if(player.getPersistentDataContainer().has(percentKey)) {
                    chance += player.getPersistentDataContainer().get(percentKey, PersistentDataType.DOUBLE);
                }
            }
        }

        return chance;
    }

    private double getItemBoostedChance(Rarity rarity, ItemStack item) {
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

        NamespacedKey rarityKey = new NamespacedKey(plugin, "boostRarity");
        if(meta.getPersistentDataContainer().has(rarityKey)) {
            if(!meta.getPersistentDataContainer().get(rarityKey, PersistentDataType.STRING).equalsIgnoreCase(rarity.getInternalName())) {
                return 0;
            }
        }

        NamespacedKey percentKey = new NamespacedKey(plugin, "boostRarityPercent");
        if(meta.getPersistentDataContainer().has(percentKey)) {
            return meta.getPersistentDataContainer().get(percentKey, PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public Rarity calculateRarity() {
        Random random = new Random();
        double randomNumber = random.nextDouble(YggdrasilsBark.config.totalRarityWeight);
        double weight = 0;
        List<Rarity> rarities = YggdrasilsBark.config.rarities;

        for(Rarity rarity : rarities) {
            weight += rarity.getWeight();
            if(randomNumber < weight) {
                return rarity;
            }
        }

        return rarities.get(rarities.size() - 1);
    }

    public Rarity calculateRarityWithBoost(Rarity boostedRarity, double percent) {
        double boostedAmount = getBoostedAmount(boostedRarity, percent);
        Random random = new Random();
        double randomNumber = random.nextDouble(YggdrasilsBark.config.totalRarityWeight + boostedAmount);
        double weight = 0;
        List<Rarity> rarities = YggdrasilsBark.config.rarities;

        for(Rarity rarity : rarities) {
            if(rarity.getInternalName().equalsIgnoreCase(boostedRarity.getInternalName())) {
                weight += boostedAmount;
            }
            weight += rarity.getWeight();
            if(randomNumber < weight) {
                return rarity;
            }
        }

        return rarities.get(rarities.size() - 1);
    }

    public Rarity getRarity(Player player) {
        for(Rarity configRarity : YggdrasilsBark.config.rarities) {
            double boosted = getPlayerBoostedChance(configRarity, player);
            if(boosted != 0) {
                return calculateRarityWithBoost(configRarity, boosted);
            }
        }

        return calculateRarity();
    }

    private double getBoostedAmount(Rarity boostedRarity, double percent) {
        double booster = 1 + percent;
        double newWeight = boostedRarity.getWeight() * booster;
        return newWeight - boostedRarity.getWeight();
    }

    public boolean spawnCreature(Block block, Tree tree, Player player) {
        if(UserManager.getPlayer(player).getSkillLevel(tree.getSkill()) < tree.getSkillLevel()) {
            return false;
        }

        if(!player.hasPermission("yggdrasilsbark.creatures." + tree.getMaterial().toString())) {
            return false;
        }

        String mob = getRandomCreature(tree);
        Location location = block.getLocation();
        MobExecutor mobExecutor = MythicBukkit.inst().getMobManager();
        MythicMob mythicMob = mobExecutor.getMythicMob(mob).get();
        mobExecutor.spawnMob(mob, location);
        String message = tree.getMessage().replace("%creature%", mythicMob.getDisplayName().get());
        player.playSound(player.getLocation(), Sound.valueOf(YggdrasilsBark.config.creatureSpawnSound), 1F, 1F);
        player.sendMessage(message);
        return true;
    }

    private String getRandomCreature(Tree tree) {
        Random random = new Random();
        return tree.getCreatures().get(random.nextInt(tree.getCreatures().size()));
    }

    public void giveReward(Player player) {
        Rarity rarity = getRarity(player);
        Reward reward = getRandomReward(rarity);
        String command = reward.getCommand().replace("%player%", player.getName());
        if(!rarity.getBroadcast().isEmpty()) {
            Bukkit.getServer().broadcastMessage(rarity.getBroadcast().replace("%player%", player.getName()));
        }

        if(!rarity.getTitle().isEmpty()) {
            player.sendTitle(rarity.getTitle(), "", 10, 70, 20);
        }

        if(rarity.getFireworks() != -1) {
            for(int i=0;i<rarity.getFireworks();i++) {
                Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK_ROCKET);
                firework.setFireworkMeta(getRandomFirework(firework));
            }
        }

        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        String message = rarity.getPrefix() + reward.getSuffix();
        player.sendMessage(message);
        plugin.getServer().getPluginManager().callEvent(new PlayerReceiveRewardEvent(player, rarity, reward));
    }

    private Reward getRandomReward(Rarity rarity) {
        List<Reward> rewards = rarity.getRewards();
        Random random = new Random();

        return rewards.get(random.nextInt(rewards.size()));
    }

    /**
     * Método para añadir probabilidad de objetos, criaturas, doble recompensa o rareza mejorada a un jugador.
     *
     * @param player Jugador al que añadir
     * @param chance Probabiliad a añadir
     * @param type Tipo de probabilidad 0 -> objetos, 1 -> criaturas, 2 -> doble, 3 -> rareza mejorada
     * @param rarity Parámetro exclusivo para el uso de rareza mejorada
     */
    public void addChanceToPlayer(Player player, double chance, int type, String rarity) {
        NamespacedKey key;
        switch(type) {
            case 0:
                key = new NamespacedKey(plugin, "itemChance");
                break;
            case 1:
                key = new NamespacedKey(plugin, "creatureChance");
                break;
            case 2:
                key = new NamespacedKey(plugin, "doubleChance");
                break;
            default:
                key = new NamespacedKey(plugin, "boostRarityPercent");
                NamespacedKey rarityKey = new NamespacedKey(plugin, "boostRarity");
                player.getPersistentDataContainer().set(rarityKey, PersistentDataType.STRING, rarity);
                break;
        }

        player.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, chance);
    }

    /**
     * Método para quitar probabilidad de objetos, criaturas, doble recompensa o rareza mejorada a un jugador.
     *
     * @param player Jugador al que quitar
     * @param chance Probabiliad a quitar
     * @param type Tipo de probabilidad 0 -> objetos, 1 -> criaturas, 2 -> doble, 3 -> rareza mejorada
     * @param rarity Parámetro exclusivo para el uso de rareza mejorada
     */
    public void removeChanceFromPlayer(Player player, double chance, int type, String rarity) {
        NamespacedKey key;
        switch(type) {
            case 0:
                key = new NamespacedKey(plugin, "itemChance");
                break;
            case 1:
                key = new NamespacedKey(plugin, "creatureChance");
                break;
            case 2:
                key = new NamespacedKey(plugin, "doubleChance");
                break;
            default:
                key = new NamespacedKey(plugin, "boostRarityPercent");
                break;
        }

        if(!player.getPersistentDataContainer().has(key)) {
            return;
        }

        double playerChance = player.getPersistentDataContainer().get(key, PersistentDataType.DOUBLE);
        playerChance -= chance;
        if(playerChance <= 0) {
            player.getPersistentDataContainer().remove(key);
            if(type > 2) {
                player.getPersistentDataContainer().remove(new NamespacedKey(plugin, "boostRarity"));
            }
        } else {
            player.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, playerChance);
        }
    }

    public void addDoubleDropChance(ItemStack item, double chance) {
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

        meta.getPersistentDataContainer().set(baseDoubleKey, PersistentDataType.DOUBLE, chance);
        item.setItemMeta(meta);
    }

    public void addItemChance(ItemStack item, double chance) {
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

        meta.getPersistentDataContainer().set(baseItemKey, PersistentDataType.DOUBLE, chance);
        item.setItemMeta(meta);
    }

    public void addCreatureChance(ItemStack item, double chance) {
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

        meta.getPersistentDataContainer().set(baseCreatureKey, PersistentDataType.DOUBLE, chance);
        item.setItemMeta(meta);
    }

    public void addBoostRarityChance(ItemStack item, double chance, String rarityName) {
        NamespacedKey percentKey = new NamespacedKey(plugin, "boostRarityPercent");
        NamespacedKey rarityKey = new NamespacedKey(plugin, "boostRarity");
        Rarity rarity = getRarityByName(rarityName);
        String loreLine = YggdrasilsBark.config.boostChance
                .replace("%boost_chance%", String.valueOf((int) (chance * 100)))
                .replace("%rarity%", rarity.getName());
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

        meta.getPersistentDataContainer().set(rarityKey, PersistentDataType.STRING, rarityName);
        meta.getPersistentDataContainer().set(percentKey, PersistentDataType.DOUBLE, chance);
        item.setItemMeta(meta);
    }

    public Rarity getRarityByName(String name) {
        for(Rarity rarity : YggdrasilsBark.config.rarities) {
            if(rarity.getInternalName().equalsIgnoreCase(name)) {
                return rarity;
            }
        }

        return null;
    }

    private FireworkMeta getRandomFirework(Firework firework) {
        Random random = new Random();
        FireworkMeta meta = firework.getFireworkMeta();
        meta.setPower(random.nextInt(3) + 1);
        meta.addEffect(FireworkEffect.builder()
                .with(getRandomFireworkType())
                .flicker(random.nextBoolean())
                .trail(random.nextBoolean())
                .withColor(Color.fromRGB(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                .withColor(Color.fromRGB(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                .withFade(Color.fromRGB(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                .build());
        return meta;
    }

    private FireworkEffect.Type getRandomFireworkType() {
        Random random = new Random();
        return FireworkEffect.Type.values()[random.nextInt(FireworkEffect.Type.values().length)];
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
