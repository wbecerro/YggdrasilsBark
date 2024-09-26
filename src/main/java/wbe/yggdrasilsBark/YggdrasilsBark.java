package wbe.yggdrasilsBark;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import wbe.yggdrasilsBark.commands.CommandListener;
import wbe.yggdrasilsBark.config.Config;
import wbe.yggdrasilsBark.config.Messages;
import wbe.yggdrasilsBark.listeners.EventListeners;

import java.io.File;

public final class YggdrasilsBark extends JavaPlugin {

    private FileConfiguration configuration;

    private CommandListener commandListener;

    private EventListeners eventListeners;

    public static Config config;

    public static Messages messages;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("YggdrasilsBark enabled correctly.");
        reloadConfiguration();

        commandListener = new CommandListener();
        getCommand("yggdrasilsbark").setExecutor(commandListener);
        eventListeners = new EventListeners();
        eventListeners.initializeListeners();
    }

    @Override
    public void onDisable() {
        reloadConfig();
        getLogger().info("YggdrasilsBark disabled correctly.");
    }

    public static YggdrasilsBark getInstance() {
        return getPlugin(YggdrasilsBark.class);
    }

    public void reloadConfiguration() {
        if(!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }
        reloadConfig();
        configuration = getConfig();
        config = new Config(configuration);
        messages = new Messages(configuration);
    }

}
