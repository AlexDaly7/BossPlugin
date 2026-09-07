package Alex.bossPlugin.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Mob;

import java.util.Map;

public class Heal extends Ability {
    // TODO: Add option for percentage heal of health, either within this ability or in another
    public Heal(Map<String, Object> data) {
        super(data);
        name = "Heal";
        lore = "Heals the boss.";
        displayItem = Material.POTION;

        if(!data.containsKey("amount")) {
            data.put("amount", 10);
        }
    }

    @Override
    public void activate(Mob mob) {
        int amount = (int) data.get("amount");

        mob.heal(amount);
    }
}
