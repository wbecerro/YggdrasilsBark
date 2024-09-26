package wbe.yggdrasilsBark.rarities;

import org.bukkit.Material;

import java.util.List;

public class Tree {

    private Material material;

    private List<String> creatures;

    private String message;

    public Tree(Material material, List<String> creatures, String message) {
        this.material = material;
        this.creatures = creatures;
        this.message = message;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public List<String> getCreatures() {
        return creatures;
    }

    public void setCreatures(List<String> creatures) {
        this.creatures = creatures;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
