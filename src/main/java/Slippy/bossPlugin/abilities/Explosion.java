package Slippy.bossPlugin.abilities;

import Slippy.bossPlugin.BossPlugin;
import Slippy.bossPlugin.util.TaskUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Mob;
import java.util.ArrayList;

public class Explosion extends Ability {

    public Explosion(int range) {
        super(range);
    }
    public void activate(Mob mob) {
        Location loc = mob.getLocation();

        // Get players within radius
        ArrayList<Player> players = new ArrayList<Player>(loc.getNearbyPlayers(range));
        if(players.getFirst()!=null) {
            World world = mob.getWorld();
            JavaPlugin plugin = BossPlugin.getPlugin();

            for(int i=0;i<=players.size()-1;i++) {
                plugin.getLogger().info("I: "+i);
                Location playerLoc = players.get(i).getLocation();
                // Spawn warning particles on timer for each player.
                TaskUtil.runTimedTask(() -> {
                    world.spawnParticle(
                            Particle.GLOW,
                            playerLoc,
                            60,
                            1.4, 0,1.4);
                    world.playSound(playerLoc, Sound.BLOCK_ANVIL_DESTROY, 1, 1);
                }, 0, 20, 2);
                // Spawn explosion and extra explosion particles to make it look larger.
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    world.createExplosion(playerLoc.clone().add(0,1.2,0), 2, true, false, null);
                    world.spawnParticle(
                            Particle.EXPLOSION,
                            playerLoc.clone().add(0,0.5,0),
                            10,
                            1,1,1,
                            0
                    );
                }, 70);
            }

        }
    }
}
