package Slippy.bossPlugin.abilities;

import org.bukkit.entity.Mob;

import java.util.Map;

public class Heal extends Ability {

    public Heal(Map<String, Object> data) {
        super(data);
    }

    @Override
    public void activate(Mob mob) {
        mob.heal(10);
    }
}
