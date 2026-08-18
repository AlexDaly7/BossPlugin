package Alex.bossPlugin.passiveEffects;

import org.bukkit.entity.Mob;

import java.util.Map;

public class PassiveEffect {
    protected Map<String, Object> data;

    public PassiveEffect(Map<String, Object> data) {
        this.data = data;
    }

    public void activate(Mob mob) {

    }
}
