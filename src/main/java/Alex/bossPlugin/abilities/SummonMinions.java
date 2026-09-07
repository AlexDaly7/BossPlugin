package Alex.bossPlugin.abilities;

import Alex.bossPlugin.util.TaskUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Mob;

import java.util.Map;

public class SummonMinions extends Ability {

    public SummonMinions(Map<String, Object> data) {
        super(data);
        name = "Summon Minions";
        lore = "Summons mobs of the same type around the boss.";
        displayItem = Material.POTION;

        if(!data.containsKey("range")) {
            data.put("range", 10);
        }

        if(!data.containsKey("amount")) {
            data.put("amount", 4);
        }
    }

    @Override
    public void activate(Mob mob) {
        int range = (int) data.get("range");
        int amount = (int) data.get("amount");
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
