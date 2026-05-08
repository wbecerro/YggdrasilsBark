package wbe.yggdrasilsBark.rarities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class RewardCommand extends Reward {

    private String command;

    public RewardCommand(String suffix, String command) {
        super(suffix);
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void giveReward(Player player) {
        String command = getCommand().replace("%player%", player.getName());
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}
