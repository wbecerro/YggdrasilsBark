package wbe.yggdrasilsBark.config;

import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import wbe.yggdrasilsBark.rarities.Reward;
import wbe.yggdrasilsBark.rarities.Tree;
import wbe.yggdrasilsBark.rarities.Rarity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Config {

    private FileConfiguration config;

    public double baseItemChance;
    public double baseCreatureChance;
    public double baseDoubleDropChance;
    public String activeMode;
    public String inactiveMode;

    public String doubleDropSound;
    public String changeModeSound;
    public String creatureSpawnSound;

    public String axeName;
    public List<String> axeLore;
    public String axeMode;
    public String axeItemChance;
    public String axeCreatureChance;
    public String axeDoubleChance;
    public String boostChance;

    public List<Tree> trees = new ArrayList<>();
    public List<Rarity> rarities = new ArrayList<>();
    public double totalRarityWeight = 0;

    public Config(FileConfiguration config) {
        this.config = config;

        baseItemChance = config.getDouble("Config.baseItemChance");
        baseCreatureChance = config.getDouble("Config.baseCreatureChance");
        baseDoubleDropChance = config.getDouble("Config.baseDoubleDropChance");
        activeMode = config.getString("Config.activeMode").replace("&", "§");
        inactiveMode = config.getString("Config.inactiveMode").replace("&", "§");
        doubleDropSound = config.getString("Sounds.doubleDropSound");
        changeModeSound = config.getString("Sounds.changeModeSound");
        creatureSpawnSound = config.getString("Sounds.creatureSpawnSound");
        axeName = config.getString("Items.Axe.name").replace("&", "§");
        axeLore = config.getStringList("Items.Axe.lore");
        axeMode = config.getString("Items.Axe.mode").replace("&", "§");
        axeItemChance = config.getString("Items.Axe.itemChance").replace("&", "§");
        axeCreatureChance = config.getString("Items.Axe.creatureChance").replace("&", "§");
        axeDoubleChance = config.getString("Items.Axe.doubleChance").replace("&", "§");
        boostChance = config.getString("Items.Axe.boostChance").replace("&", "§");

        loadTrees();
        loadRarities();
    }

    private void loadRarities() {
        Set<String> configRarities = config.getConfigurationSection("Rarities").getKeys(false);
        for(String rarity : configRarities) {
            String name = config.getString("Rarities." + rarity + ".name").replace("&", "§");
            String prefix = config.getString("Rarities." + rarity + ".prefix").replace("&", "§");
            double weight = config.getDouble("Rarities." + rarity + ".weight");
            totalRarityWeight += weight;
            List<Reward> rewards = getRewards(rarity);
            String broadcast = "";
            if(config.contains("Rarities." + rarity + ".broadcast")) {
                broadcast = config.getString("Rarities." + rarity + ".broadcast").replace("&", "§");
            }
            String title = "";
            if(config.contains("Rarities." + rarity + ".title")) {
                title = config.getString("Rarities." + rarity + ".title").replace("&", "§");
            }
            int fireworks = -1;
            if(config.contains("Rarities." + rarity + ".fireworks")) {
                fireworks = config.getInt("Rarities." + rarity + ".fireworks");
            }
            rarities.add(new Rarity(rarity, name, prefix, weight, rewards, broadcast, title, fireworks));
        }
    }

    private void loadTrees() {
        Set<String> configTrees = config.getConfigurationSection("Trees").getKeys(false);
        for(String tree : configTrees) {
            String message = config.getString("Trees." + tree + ".message").replace("&", "§");
            List<String> creatures = config.getStringList("Trees." + tree + ".creatures");
            PrimarySkillType skill = PrimarySkillType.valueOf(config.getString("Trees." + tree + ".mcmmoSkill").toUpperCase());
            int level = config.getInt("Trees." + tree + ".mcmmoLevel");
            trees.add(new Tree(Material.valueOf(tree), creatures, skill, level, message));
        }
    }

    private List<Reward> getRewards(String rarity) {
        List<Reward> finalRewards = new ArrayList<>();
        Set<String> rewards = config.getConfigurationSection("Rarities." + rarity + ".rewards").getKeys(false);
        for(String reward : rewards) {
            String suffix = config.getString("Rarities." + rarity + ".rewards." + reward + ".suffix").replace("&", "§");
            String command = config.getString("Rarities." + rarity + ".rewards." + reward + ".command");
            finalRewards.add(new Reward(suffix, command));
        }

        return finalRewards;
    }
}
