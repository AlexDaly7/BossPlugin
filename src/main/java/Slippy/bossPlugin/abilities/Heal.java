package Slippy.bossPlugin.abilities;

import org.bukkit.entity.Mob;

public class Heal extends Ability {

    public Heal(int range) {
        super(range);
    }

    @Override
    public void activate(Mob mob) {
        mob.heal(10);
    }
}
