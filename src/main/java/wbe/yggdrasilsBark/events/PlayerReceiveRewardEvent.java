package wbe.yggdrasilsBark.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import wbe.yggdrasilsBark.rarities.Rarity;
import wbe.yggdrasilsBark.rarities.Reward;

public class PlayerReceiveRewardEvent extends Event implements Cancellable {

    private Player player;

    private Rarity rarity;

    private Reward reward;

    private boolean isCancelled;

    private static final HandlerList handlers = new HandlerList();

    public PlayerReceiveRewardEvent(Player player, Rarity rarity, Reward reward) {
        this.player = player;
        this.rarity = rarity;
        this.reward = reward;
    }

    public Player getPlayer() {
        return player;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public Reward getReward() {
        return reward;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
