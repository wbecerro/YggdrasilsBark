package wbe.yggdrasilsBark.rarities;

import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import org.bukkit.Material;

import java.util.List;

public class Tree {

    private Material material;

    private List<String> creatures;

    private PrimarySkillType skill;

    private int skillLevel;

    private String message;

    public Tree(Material material, List<String> creatures, PrimarySkillType skill, int skillLevel, String message) {
        this.material = material;
        this.creatures = creatures;
        this.skill = skill;
        this.skillLevel = skillLevel;
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

    public PrimarySkillType getSkill() {
        return skill;
    }

    public void setSkill(PrimarySkillType skill) {
        this.skill = skill;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
