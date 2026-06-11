package Slippy.bossPlugin.abilities;

import Slippy.bossPlugin.BossPlugin;
import Slippy.bossPlugin.util.TaskUtil;
import org.bukkit.*;
import org.bukkit.entity.Boss;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public enum AbilityType {
    SUMMON_SPIDERS {
        @Override
        public void activate(Mob mob, int range) {
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
    },
    EVOKER_FANG_STRIKE {
      @Override
      public void activate(Mob mob, int range) {
          Location loc = mob.getLocation();

          ArrayList<Player> players = new ArrayList<Player>(loc.getNearbyPlayers(range));
          if(players.getFirst()!=null) {
              JavaPlugin plugin = BossPlugin.getPlugin();
              World world = mob.getWorld();
              Location playerLoc = players.getFirst().getLocation();
              double distance = loc.distance(playerLoc);
              plugin.getLogger().info("Distance: " + distance + "\n");

              double x = (playerLoc.getX() - loc.getX()) / distance;
              double z = (playerLoc.getZ() - loc.getZ()) / distance;
              double y = loc.getY();
              Location[] currentLoc = new Location[2];
              currentLoc[0] = loc;

              // Particle warning task
              TaskUtil.runTimedTask(() -> {
                  currentLoc[0] = new Location(world, currentLoc[0].getX() + x, y, currentLoc[0].getZ() + z);
                  world.spawnParticle(
                          Particle.LARGE_SMOKE, currentLoc[0],
                          4,
                          0.3, 0.3, 0.3,
                          0);
                  world.playSound(currentLoc[0], Sound.BLOCK_BONE_BLOCK_BREAK, 1, 1);
              }, 0, 1, (int) Math.floor(distance) * 2);

              // Set starting location and then run evoker fang task
              currentLoc[1] = loc;
              TaskUtil.runTimedTask(() -> {
                  currentLoc[1] = new Location(world, currentLoc[1].getX() + x, y, currentLoc[1].getZ() + z);
                  world.spawnEntity(currentLoc[1], EntityType.EVOKER_FANGS);
                  world.playSound(currentLoc[1], Sound.ENTITY_EVOKER_DEATH, 0.4f, 1);
              }, 15, 1, (int) Math.floor(distance) * 2);
              world.playSound(currentLoc[1], Sound.BLOCK_AMETHYST_CLUSTER_FALL, 0.8f, 1);
          }
      }
    },
    EXPLOSION {
        public void activate(Mob mob, int range) {
            Location loc = mob.getLocation();


            ArrayList<Player> players = new ArrayList<Player>(loc.getNearbyPlayers(20));
            if(players.getFirst()!=null) {
                World world = mob.getWorld();
                JavaPlugin plugin = BossPlugin.getPlugin();
                for(int i=0;i<=players.size()-1;i++) {
                    plugin.getLogger().info("I: "+i);
                    Location playerLoc = players.get(i).getLocation();
                    TaskUtil.runTimedTask(() -> {
                        world.spawnParticle(
                                Particle.GLOW,
                                playerLoc,
                                60,
                                1.4, 0,1.4);
                        world.playSound(playerLoc, Sound.BLOCK_ANVIL_DESTROY, 1, 1);
                    }, 0, 20, 2);
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
    };

    public abstract void activate(Mob mob, int range);
}