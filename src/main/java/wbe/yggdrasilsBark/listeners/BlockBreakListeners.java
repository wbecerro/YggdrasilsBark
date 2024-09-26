package wbe.yggdrasilsBark.listeners;

import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
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
        int creatureChance = utilities.getPlayerCreatureChance(player);
        int itemChance = utilities.getPlayerItemChance(player);
        int doubleChance = utilities.getPlayerDoubleChance(player);

        int iterations = 1;
        if(random.nextInt(100) + 1 <= doubleChance) {
            iterations++;
            player.sendMessage(YggdrasilsBark.messages.doubleDrop);
            player.playSound(player.getLocation(), Sound.valueOf(YggdrasilsBark.config.doubleDropSound), 1F, 1F);
        }

        for(int i=0;i<iterations;i++) {
            if(random.nextInt(100) + 1 <= creatureChance) {
                utilities.spawnCreature(event.getBlock(), treeType, player);
                continue;
            }


            if(random.nextInt(100 ) + 1 <= itemChance) {
                utilities.giveReward(treeType, player);
                continue;
            }
        }
    }
}
