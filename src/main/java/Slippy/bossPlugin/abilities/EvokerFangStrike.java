package Slippy.bossPlugin.abilities;

import Slippy.bossPlugin.BossPlugin;
import Slippy.bossPlugin.util.TaskUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class EvokerFangStrike extends Ability {

    public EvokerFangStrike(int range) {
        super(range);
    }

    @Override
    public void activate(Mob mob) {
        Location loc = mob.getLocation();


        if(!loc.getNearbyPlayers(range).isEmpty()) {
            ArrayList<Player> players = new ArrayList<Player>(loc.getNearbyPlayers(range));
            World world = mob.getWorld();
            Location playerLoc = players.getFirst().getLocation();

            // Get first / nearest player and get distance.
            double distance = loc.distance(playerLoc);
            // Get x and z cords divided by distance, and mob y.
            double x = (playerLoc.getX() - loc.getX()) / distance;
            double z = (playerLoc.getZ() - loc.getZ()) / distance;
            double y = mob.getY();
            // Create Location array to access inside of bukkit tasks.
            Location[] currentLoc = new Location[2];
            // Store 2 copies of current location to iterate on.
            currentLoc[0] = loc;
            currentLoc[1] = loc;

            // Particle warning task, iterate position towards and beyond player location.
            TaskUtil.runTimedTask(() -> {
                currentLoc[0] = new Location(world, currentLoc[0].getX() + x, y, currentLoc[0].getZ() + z);
                world.spawnParticle(
                        Particle.LARGE_SMOKE, currentLoc[0],
                        4,
                        0.3, 0.3, 0.3,
                        0);
                world.playSound(currentLoc[0], Sound.BLOCK_BONE_BLOCK_BREAK, 1, 1);
            }, 0, 1, (int) Math.floor(distance) * 2);

            // Summon evoker fang entities, iterate towards and beyond player location.
            TaskUtil.runTimedTask(() -> {
                currentLoc[1] = new Location(world, currentLoc[1].getX() + x, y, currentLoc[1].getZ() + z);
                world.spawnEntity(currentLoc[1], EntityType.EVOKER_FANGS);
                world.playSound(currentLoc[1], Sound.ENTITY_EVOKER_DEATH, 0.4f, 1);
            }, 15, 1, (int) Math.floor(distance) * 2);
            world.playSound(currentLoc[1], Sound.BLOCK_AMETHYST_CLUSTER_FALL, 0.8f, 1);
        }
    }
}
