package wbe.yggdrasilsBark.rarities;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class RewardItem extends Reward {

    private ItemStack item;

    public RewardItem(String suffix, ItemStack item) {
        super(suffix);
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public void giveReward(Player player) {
        Location playerLocation = player.getLocation();
        Item rewardItem = playerLocation.getWorld().dropItem(playerLocation, getItem());
        rewardItem.setOwner(player.getUniqueId());
        rewardItem.setPickupDelay(0);
    }
}
