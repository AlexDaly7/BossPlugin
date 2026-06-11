package Slippy.bossPlugin.abilities;

import Slippy.bossPlugin.util.TaskUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;

public class SummonSpiders extends Ability {

    public SummonSpiders(int range) {
        super(range);
    }

    @Override
    public void activate(Mob mob) {
        // TODO: Ability needs refactoring, poor implementation of cord selecting system.
        Location loc = mob.getLocation();
        World world = mob.getWorld();
        double ranX = Math.random()*10;
        double ranZ = Math.random()*10;
        int count = 0;
        double[] y = new double[1];
        y[0] = mob.getY();
        TaskUtil.runTimedTask(() -> {
            world.spawnParticle(Particle.FLAME, loc.add(ranX, y[0], ranZ), 1, 0.5, 0.5, 0.5, 0);
            world.spawnParticle(Particle.FLAME, loc.add(ranX*-1, y[0], ranZ), 1, 0.5, 0.5, 0.5, 0);
            world.spawnParticle(Particle.FLAME, loc.add(ranX, y[0], ranZ*-1), 1, 0.5, 0.5, 0.5, 0);
            world.spawnParticle(Particle.FLAME, loc.add(ranX*-1, y[0], ranZ*-1), 1, 0.5, 0.5, 0.5, 0);
            y[0] += 0.5;
        },0, 1, 10);


        world.spawnEntity(loc.add(ranX, 0, ranZ), EntityType.SPIDER);
        world.spawnEntity(loc.add(ranX*-1, 0, ranZ), EntityType.SPIDER);
        world.spawnEntity(loc.add(ranX, 0, ranZ*-1), EntityType.SPIDER);
        world.spawnEntity(loc.add(ranX*-1, 0, ranZ*-1), EntityType.SPIDER);
    }
}
