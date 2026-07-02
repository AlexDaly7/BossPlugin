package Slippy.bossPlugin.abilities;

import org.bukkit.entity.Mob;

import java.util.Map;

public class Heal extends Ability {
    // TODO: Add option for percentage heal of health, either within this ability or in another
    public Heal(Map<String, Object> data) {
        super(data);
    }

    @Override
    public void activate(Mob mob) {
        int amount = data.containsKey("amount") ? (int) data.get("amount") : 10;

        mob.heal(amount);
    }
}
