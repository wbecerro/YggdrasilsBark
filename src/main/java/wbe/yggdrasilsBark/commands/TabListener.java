package wbe.yggdrasilsBark.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import wbe.yggdrasilsBark.YggdrasilsBark;
import wbe.yggdrasilsBark.rarities.Rarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabListener implements TabCompleter {

    private final List<String> subCommands = Arrays.asList("help", "axe", "double", "itemChance", "creatureChance",
            "boostRarity", "stats", "reload");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if(!command.getName().equalsIgnoreCase("YggdrasilsBark")) {
            return completions;
        }

        // Mostrar subcomandos
        if(args.length == 1) {
            StringUtil.copyPartialMatches(args[0], subCommands, completions);
        }

        // Argumento 1
        if(args.length == 2) {
            switch(args[0].toLowerCase()) {
                case "axe":
                    List<String> materials = new ArrayList<>();
                    Arrays.asList(Material.values()).forEach((material -> {
                        if(args[1].isEmpty()) {
                            materials.add(material.toString());
                        } else if(material.toString().startsWith(args[1])) {
                            materials.add(material.toString());
                        }
                    }));
                    completions.addAll(materials);
                    break;
                case "itemchance":
                case "creaturechance":
                case "double":
                    completions.add("<Probabilidad>");
                    break;
                case "boostrarity":
                    for(Rarity rarity : YggdrasilsBark.config.rarities) {
                        if(args[1].isEmpty()) {
                            completions.add(rarity.getInternalName());
                        } else if(rarity.getInternalName().startsWith(args[1])) {
                            completions.add(rarity.getInternalName());
                        }
                    }
                    break;
            }
        }

        // Argumento 2
        if(args.length == 3) {
            switch(args[0].toLowerCase()) {
                case "axe":
                    completions.add("<Probabilidad objeto>");
                    break;
                case "boostrarity":
                    completions.add("<Procentaje en base 0>");
                    break;
            }
        }

        // Argumento 3
        if(args.length == 4) {
            switch(args[0].toLowerCase()) {
                case "axe":
                    completions.add("<Probabilidad criatura>");
                    break;
            }
        }

        // Argumento 4
        if(args.length == 5) {
            switch(args[0].toLowerCase()) {
                case "axe":
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        if(args[4].isEmpty()) {
                            completions.add(player.getName());
                        } else if(player.getName().startsWith(args[4])) {
                            completions.add(player.getName());
                        }
                    }
                    break;
            }
        }

        return completions;
    }
}
