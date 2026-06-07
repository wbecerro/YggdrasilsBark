package wbe.yggdrasilsBark.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import wbe.yggdrasilsBark.YggdrasilsBark;

public class PapiExtension extends PlaceholderExpansion {

    @Override
    public String getAuthor() {
        return "wbe";
    }

    @Override
    public String getIdentifier() {
        return "YggdrasilsBark";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("itemchance")) {
            return String.format("%.2f", YggdrasilsBark.utilities.getPlayerItemChance(player.getPlayer()));
        } else if(params.equalsIgnoreCase("creaturechance")) {
            return String.format("%.2f", YggdrasilsBark.utilities.getPlayerCreatureChance(player.getPlayer()));
        } else if(params.equalsIgnoreCase("doublechance")) {
            return String.format("%.2f", YggdrasilsBark.utilities.getPlayerDoubleChance(player.getPlayer()));
        } else if(params.contains("chance")) {
            String rarityName = params.replace("chance", "");
            return YggdrasilsBark.utilities.showRarityChance(rarityName, player.getPlayer());
        }

        return null;
    }
}
