package wbe.yggdrasilsBark.listeners;

import org.bukkit.plugin.PluginManager;
import wbe.yggdrasilsBark.YggdrasilsBark;

public class EventListeners {

    private YggdrasilsBark plugin = YggdrasilsBark.getInstance();

    public void initializeListeners() {
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerDropItemListeners(), plugin);
        pluginManager.registerEvents(new BlockBreakListeners(), plugin);
    }
}
