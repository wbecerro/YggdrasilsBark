package wbe.yggdrasilsBark.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wbe.yggdrasilsBark.YggdrasilsBark;
import wbe.yggdrasilsBark.items.Axe;
import wbe.yggdrasilsBark.utils.Utilities;

public class CommandListener implements CommandExecutor {

    private YggdrasilsBark plugin = YggdrasilsBark.getInstance();

    private Utilities utilities = new Utilities();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("YggdrasilsBark")) {
            Player player = null;
            if(sender instanceof Player) {
                player = (Player) sender;
            }
            if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
                if(!sender.hasPermission("yggdrasilsbark.command.help")) {
                    sender.sendMessage(YggdrasilsBark.messages.noPermission);
                    return false;
                }

                for(String line : YggdrasilsBark.messages.help) {
                    sender.sendMessage(line.replace("&", "§"));
                }
            } else if(args[0].equalsIgnoreCase("axe")) {
                if(!sender.hasPermission("yggdrasilsbark.command.axe")) {
                    player.sendMessage(YggdrasilsBark.messages.noPermission);
                    return false;
                }

                if(args.length < 4) {
                    sender.sendMessage(YggdrasilsBark.messages.notEnoughArgs);
                    sender.sendMessage(YggdrasilsBark.messages.axeArguments);
                    return false;
                }

                Material material;
                try {
                    material = Material.valueOf(args[1].toUpperCase());
                } catch(IllegalArgumentException ex) {
                    sender.sendMessage(YggdrasilsBark.messages.invalidMaterial);
                    return false;
                }
                int itemChance = Integer.valueOf(args[2]);
                int creatureChance = Integer.valueOf(args[3]);

                Axe axe = new Axe(material, itemChance, creatureChance);
                if(args.length > 4) {
                    Bukkit.getPlayer(args[4]).getInventory().addItem(axe);
                    Bukkit.getPlayer(args[4]).sendMessage(YggdrasilsBark.messages.axeGiven);
                } else {
                    player.getInventory().addItem(axe);
                    player.sendMessage(YggdrasilsBark.messages.axeGiven);
                }
            } else if(args[0].equalsIgnoreCase("reload")) {
                if(!sender.hasPermission("yggdrasilsbark.command.reload")) {
                    sender.sendMessage(YggdrasilsBark.messages.noPermission);
                    return false;
                }

                plugin.reloadConfiguration();
                sender.sendMessage(YggdrasilsBark.messages.reload);
            } else if(args[0].equalsIgnoreCase("double")) {
                if(!sender.hasPermission("yggdrasilsbark.command.double")) {
                    sender.sendMessage(YggdrasilsBark.messages.noPermission);
                    return false;
                }

                if(args.length < 2) {
                    sender.sendMessage(YggdrasilsBark.messages.notEnoughArgs);
                    sender.sendMessage(YggdrasilsBark.messages.doubleDropArguments);
                    return false;
                }

                utilities.addDoubleDropChance(player.getInventory().getItemInMainHand(), Integer.valueOf(args[1]));
                sender.sendMessage(YggdrasilsBark.messages.doubleDropAdded);
            } else if(args[0].equalsIgnoreCase("itemChance")) {
                if(!sender.hasPermission("yggdrasilsbark.command.itemChance")) {
                    sender.sendMessage(YggdrasilsBark.messages.noPermission);
                    return false;
                }

                if(args.length < 2) {
                    sender.sendMessage(YggdrasilsBark.messages.notEnoughArgs);
                    sender.sendMessage(YggdrasilsBark.messages.itemChanceArguments);
                    return false;
                }

                utilities.addItemChance(player.getInventory().getItemInMainHand(), Integer.valueOf(args[1]));
                sender.sendMessage(YggdrasilsBark.messages.itemChanceAdded);
            } else if(args[0].equalsIgnoreCase("creatureChance")) {
                if(!sender.hasPermission("yggdrasilsbark.command.creatureChance")) {
                    sender.sendMessage(YggdrasilsBark.messages.noPermission);
                    return false;
                }

                if(args.length < 2) {
                    sender.sendMessage(YggdrasilsBark.messages.notEnoughArgs);
                    sender.sendMessage(YggdrasilsBark.messages.creatureChanceArguments);
                    return false;
                }

                utilities.addCreatureChance(player.getInventory().getItemInMainHand(), Integer.valueOf(args[1]));
                sender.sendMessage(YggdrasilsBark.messages.creatureChanceAdded);
            } else if(args[0].equalsIgnoreCase("stats")) {
                if (!sender.hasPermission("yggdrasilsbark.command.stats")) {
                    sender.sendMessage(YggdrasilsBark.messages.noPermission);
                    return false;
                }

                String itemChance = String.valueOf(utilities.getPlayerItemChance(player));
                String creatureChance = String.valueOf(utilities.getPlayerCreatureChance(player));
                String doubleChance = String.valueOf(utilities.getPlayerDoubleChance(player));
                for (String line : YggdrasilsBark.messages.stats) {
                    sender.sendMessage(line.replace("&", "§")
                            .replace("%itemChance%", itemChance)
                            .replace("%creatureChance%", creatureChance)
                            .replace("%doubleChance%", doubleChance));
                }
            }
        }
        return true;
    }
}
