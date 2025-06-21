package wbe.yggdrasilsBark.listeners;

import com.gmail.nossr50.mcMMO;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import wbe.yggdrasilsBark.YggdrasilsBark;
import wbe.yggdrasilsBark.rarities.Tree;

import java.util.Random;

public class BlockBreakListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void giveRewardsOnBreakingWood(BlockBreakEvent event) {
        if(event.isCancelled()) {
            return;
        }

        if(mcMMO.getUserBlockTracker().isIneligible(event.getBlock().getState())) {
            return;
        }
        Material brokenMaterial = event.getBlock().getType();
        Tree treeType = null;
        for(Tree tree : YggdrasilsBark.config.trees) {
            if(tree.getMaterial().equals(brokenMaterial)) {
                treeType = tree;
            }
        }

        if(treeType == null) {
            return;
        }

        Random random = new Random();
        Player player = event.getPlayer();
        double creatureChance = YggdrasilsBark.utilities.getPlayerCreatureChance(player);
        double itemChance = YggdrasilsBark.utilities.getPlayerItemChance(player);
        double doubleChance = YggdrasilsBark.utilities.getPlayerDoubleChance(player);

        if(random.nextDouble(100) <= creatureChance) {
            if(YggdrasilsBark.utilities.spawnCreature(event.getBlock(), treeType, player)) {
                if(random.nextDouble(100) <= doubleChance) {
                    player.sendMessage(YggdrasilsBark.messages.doubleDrop);
                    player.playSound(player.getLocation(), Sound.valueOf(YggdrasilsBark.config.doubleDropSound), 1F, 1F);
                    YggdrasilsBark.utilities.spawnCreature(event.getBlock(), treeType, player);
                }
                return;
            }
        }


        if(random.nextDouble(100 ) <= itemChance) {
            YggdrasilsBark.utilities.giveReward(player);
            if(random.nextDouble(100) <= doubleChance) {
                player.sendMessage(YggdrasilsBark.messages.doubleDrop);
                player.playSound(player.getLocation(), Sound.valueOf(YggdrasilsBark.config.doubleDropSound), 1F, 1F);
                YggdrasilsBark.utilities.giveReward(player);
            }
            return;
        }
    }
}
