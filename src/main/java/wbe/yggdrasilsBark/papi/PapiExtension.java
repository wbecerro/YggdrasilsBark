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
            return String.valueOf(YggdrasilsBark.utilities.getPlayerItemChance(player.getPlayer()));
        } else if(params.equalsIgnoreCase("creaturechance")) {
            return String.valueOf(YggdrasilsBark.utilities.getPlayerCreatureChance(player.getPlayer()));
        } else if(params.equalsIgnoreCase("doublechance")) {
            return String.valueOf(YggdrasilsBark.utilities.getPlayerDoubleChance(player.getPlayer()));
        }

        return null;
    }
}
