package Slippy.bossPlugin.abilities;

import org.bukkit.entity.Mob;

import java.util.Map;

public class Ability {
    protected Map<String, Object> data;

    public Ability(Map<String, Object> data) {
        this.data = data;
    }

    public void activate(Mob mob) {

    }
}
