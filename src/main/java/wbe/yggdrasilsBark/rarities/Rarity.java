package wbe.yggdrasilsBark.rarities;

import java.util.List;
import java.util.Random;

public class Rarity {

    private String internalName;

    private String prefix;

    private int weight;

    private List<Reward> rewards;

    private int rewardsSize;

    private String broadcast;

    private String title;

    public Rarity(String internalName, String prefix, int weight, List<Reward> rewards, String broadcast, String title) {
        this.internalName = internalName;
        this.prefix = prefix;
        this.weight = weight;
        this.rewards = rewards;
        this.rewardsSize = rewards.size();
        this.broadcast = broadcast;
        this.title = title;
    }

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Reward getRandomReward() {
        Random random = new Random();
        Reward reward = rewards.get(random.nextInt(rewardsSize));
        return reward;
    }
}
