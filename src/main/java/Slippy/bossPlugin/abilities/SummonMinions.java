package Slippy.bossPlugin.abilities;

import Slippy.bossPlugin.BossPlugin;
import Slippy.bossPlugin.util.TaskUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Mob;

import java.util.Map;

public class SummonMinions extends Ability {

    public SummonMinions(Map<String, Object> data) {
        super(data);
    }

    @Override
    public void activate(Mob mob) {
        // TODO: Custom particle inputs through config
        int range = data.containsKey("range") ? (int) data.get("range") : 10;
        int amount = data.containsKey("amount") ? (int) data.get("amount") : 4;
        Location loc = mob.getLocation();
        World world = mob.getWorld();

        int count = 0;
        for(int i=0;i<amount;i++) {
            Location[] mobLoc = new Location[1];
            mobLoc[0] = loc.clone().add((Math.random()*(range*2))-range, 2, (Math.random()*(range*2))-range);
            TaskUtil.runTimedTaskWithEnd(() -> {
                world.spawnParticle(Particle.PORTAL,
                        mobLoc[0],
                        70,
                        0.5, 1, 0.5
                        );
            }, 0, 5, 6, () -> {
                world.spawnEntity(mobLoc[0], mob.getType());
            });
        }
    }
}
