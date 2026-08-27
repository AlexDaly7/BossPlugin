package Alex.bossPlugin.bosses;

import Alex.bossPlugin.BossPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class BossManager {
    static BukkitTask[] task = new BukkitTask[3];
    static JavaPlugin plugin = BossPlugin.getPlugin();
    static ArrayList<BaseBoss> bosses = new ArrayList<>();

    public static void loadBosses(ArrayList<BaseBoss> loadedBosses) {
        stop();
        bosses = loadedBosses;
        start();
    }

    public static void start() {
        // Spawn bosses on plugin start
        plugin.getLogger().info("Starting boss manager");
        for(BaseBoss boss : bosses) {
            boss.spawnBoss();
        }

        // Task that ticks abilities, and respawn timer.
        // Runs every 20 ticks (1 second)
        task[0] = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for(BaseBoss boss : bosses) {
                if(!boss.isBossDead()) {
                    boss.tickAbilities();
                } else {
                    boss.tickRespawn();
                }
            }
        }, 0L, 20L);

        // Task that checks boss health and updates current phase.
        // Runs every 10 ticks (Half a second)
        task[1] = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for(BaseBoss boss : bosses) {
                boss.tickPhase();
            }
        }, 1L, 10L);

        // Task that ticks boss bar, removes bosses from arraylist once dead etc...
        // Runs every 5 ticks (4 times a second)
        task[2] = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for(BaseBoss boss : bosses) {
                if(boss.isBossDead()) {
                    boss.removeBossBar();
                } else {
                    boss.tickBossBar();
                }
                //plugin.getLogger().info(boss.mob.getName()+": ticks boss bar");
            }
        }, 0L, 5L);
    }

    public static void stop() {
        for(BukkitTask task : task) {
            if(task!=null) {
                task.cancel();
            }
        }
        for(BaseBoss boss : bosses) {
            try {
                plugin.getLogger().info(boss.getName()+" despawned");
                boss.despawnBoss();
            } catch (Exception e) {
                plugin.getLogger().warning("There was a problem despawning "+boss.getName());
            }
        }

    }


    public static void add(BaseBoss boss) {
        bosses.add(boss);
    }

    public static List<BaseBoss> getBosses() {
        return bosses;
    }
}
