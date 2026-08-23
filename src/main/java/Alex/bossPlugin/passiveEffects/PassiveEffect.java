package Alex.bossPlugin.passiveEffects;

import org.bukkit.entity.Mob;

import java.util.Map;

public class PassiveEffect {
    int amplifier;
    int range;

    public PassiveEffect(Map<String, Object> data) {
        range = data.containsKey("range") ? (int) data.get("range") : 10;
        amplifier = data.containsKey("amplifier") ? (int) data.get("amplifier") : 2;
    }

    public void activate(Mob mob) {

    }

    public int getRange() {
        return range;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public Map<String, Object> getData() {
        return Map.of();
    }

}
