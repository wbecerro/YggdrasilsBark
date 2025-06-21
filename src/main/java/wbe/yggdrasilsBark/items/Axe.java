package wbe.yggdrasilsBark.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.yggdrasilsBark.YggdrasilsBark;

import java.util.ArrayList;

public class Axe extends ItemStack {

    private YggdrasilsBark plugin = YggdrasilsBark.getInstance();

    public Axe(Material material, double itemChance, double creatureChance) {
        super(material);

        ItemMeta meta;
        if(hasItemMeta()) {
            meta = getItemMeta();
        } else {
            meta = Bukkit.getItemFactory().getItemMeta(material);
        }

        meta.setDisplayName(YggdrasilsBark.config.axeName);

        ArrayList<String> lore = new ArrayList<>();
        for(String line : YggdrasilsBark.config.axeLore) {
            lore.add(line.replace("&", "§"));
        }
        lore.add(YggdrasilsBark.config.axeMode.replace("%mode%", YggdrasilsBark.config.activeMode));
        lore.add(YggdrasilsBark.config.axeItemChance.replace("%item_chance%", String.valueOf(itemChance)));
        lore.add(YggdrasilsBark.config.axeCreatureChance.replace("%creature_chance%", String.valueOf(creatureChance)));
        meta.setLore(lore);
        setItemMeta(meta);

        setKeys(itemChance, creatureChance);
    }

    private void setKeys(double itemChance, double creatureChance) {
        ItemMeta meta = getItemMeta();
        NamespacedKey axeKey = new NamespacedKey(plugin, "YBAxe");
        NamespacedKey modeKey = new NamespacedKey(plugin, "AxeMode");
        NamespacedKey itemKey = new NamespacedKey(plugin, "itemChance");
        NamespacedKey creatureKey = new NamespacedKey(plugin, "creatureChance");

        meta.getPersistentDataContainer().set(axeKey, PersistentDataType.BOOLEAN, true);
        meta.getPersistentDataContainer().set(modeKey, PersistentDataType.BOOLEAN, true);
        meta.getPersistentDataContainer().set(itemKey, PersistentDataType.DOUBLE, itemChance);
        meta.getPersistentDataContainer().set(creatureKey, PersistentDataType.DOUBLE, creatureChance);

        setItemMeta(meta);
    }
}
