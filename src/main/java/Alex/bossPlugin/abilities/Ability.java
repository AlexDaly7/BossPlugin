package Alex.bossPlugin.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class Ability {
    protected Map<String, Object> data;
    protected String name;
    protected String lore;
    protected Material displayItem;

    public Ability(Map<String, Object> data) {
        this.data = data;
    }

    public void activate(Mob mob) {

    }

    public String getName() {
        return name;
    }

    public String getLore() {
        return lore;
    }

    public Material getItem() {
        return displayItem;
    }
}
