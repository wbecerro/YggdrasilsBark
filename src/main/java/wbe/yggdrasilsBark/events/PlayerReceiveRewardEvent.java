package wbe.yggdrasilsBark.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import wbe.yggdrasilsBark.rarities.Rarity;
import wbe.yggdrasilsBark.rarities.Reward;

public class PlayerReceiveRewardEvent extends PlayerEvent {

    private Rarity rarity;

    private Reward reward;

    public PlayerReceiveRewardEvent(Player player, Rarity rarity, Reward reward) {
        super(player);
        this.rarity = rarity;
        this.reward = reward;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public Reward getReward() {
        return reward;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
