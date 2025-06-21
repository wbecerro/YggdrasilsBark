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
import wbe.yggdrasilsBark.utils.Utilities;

import java.util.Random;

public class BlockBreakListeners implements Listener {

    private YggdrasilsBark plugin = YggdrasilsBark.getInstance();

    private Utilities utilities = new Utilities();

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
        double creatureChance = utilities.getPlayerCreatureChance(player);
        double itemChance = utilities.getPlayerItemChance(player);
        double doubleChance = utilities.getPlayerDoubleChance(player);

        if(random.nextDouble(100) <= creatureChance) {
            if(utilities.spawnCreature(event.getBlock(), treeType, player)) {
                if(random.nextDouble(100) <= doubleChance) {
                    player.sendMessage(YggdrasilsBark.messages.doubleDrop);
                    player.playSound(player.getLocation(), Sound.valueOf(YggdrasilsBark.config.doubleDropSound), 1F, 1F);
                    utilities.spawnCreature(event.getBlock(), treeType, player);
                }
                return;
            }
        }


        if(random.nextDouble(100 ) <= itemChance) {
            utilities.giveReward(player);
            if(random.nextDouble(100) <= doubleChance) {
                player.sendMessage(YggdrasilsBark.messages.doubleDrop);
                player.playSound(player.getLocation(), Sound.valueOf(YggdrasilsBark.config.doubleDropSound), 1F, 1F);
                utilities.giveReward(player);
            }
            return;
        }
    }
}
