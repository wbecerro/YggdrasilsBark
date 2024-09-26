package wbe.yggdrasilsBark.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.yggdrasilsBark.YggdrasilsBark;
import wbe.yggdrasilsBark.utils.Utilities;

public class PlayerDropItemListeners implements Listener {

    private YggdrasilsBark plugin = YggdrasilsBark.getInstance();

    private Utilities utilities = new Utilities();

    @EventHandler(priority = EventPriority.NORMAL)
    public void switchModeOnDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if(!player.isSneaking()) {
            return;
        }

        ItemStack droppedItem = event.getItemDrop().getItemStack();
        if(droppedItem.getType().equals(Material.AIR)) {
            return;
        }

        ItemMeta meta = droppedItem.getItemMeta();
        if(meta == null) {
            return;
        }

        NamespacedKey modeKey = new NamespacedKey(plugin, "AxeMode");
        if(!meta.getPersistentDataContainer().has(modeKey)) {
            return;
        }

        boolean newMode = !meta.getPersistentDataContainer().get(modeKey, PersistentDataType.BOOLEAN);
        ItemStack changedItem = utilities.changeAxeMode(droppedItem, newMode);
        meta = droppedItem.getItemMeta();
        meta.getPersistentDataContainer().set(modeKey, PersistentDataType.BOOLEAN, newMode);
        droppedItem.setItemMeta(meta);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(YggdrasilsBark.messages.modeChanged));
        player.playSound(player.getLocation(), Sound.valueOf(YggdrasilsBark.config.changeModeSound), 1F, 1F);
        event.setCancelled(true);
    }
}
