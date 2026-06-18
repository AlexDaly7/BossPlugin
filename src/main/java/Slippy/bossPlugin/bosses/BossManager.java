package Slippy.bossPlugin.bosses;

import Slippy.bossPlugin.BossPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class BossManager {
    static BukkitTask[] task = new BukkitTask[3];
    static JavaPlugin plugin = BossPlugin.getPlugin();
    static ArrayList<BaseBoss> bosses = new ArrayList<>();

    public static void loadBosses(ArrayList<BaseBoss> loadedBosses) {
        bosses = loadedBosses;
    }

    public static void start() {
        // Task that ticks abilities, runs every second
        for(BaseBoss boss : bosses) {
            boss.spawnBoss();
        }
        task[0] = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for(BaseBoss boss : bosses) {
                boss.tickAbilities();
            }
        }, 0L, 20L);

        // Task that checks boss health and updates current phase.
        task[1] = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for(BaseBoss boss : bosses) {
                boss.tickPhase();
            }
        }, 1L, 10L);

        // Task that ticks boss bar, removes bosses from arraylist once dead etc...
        // Runs 4 times a second.
        task[2] = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for(BaseBoss boss : bosses) {
                if(boss.isBossDead()) {
                    boss.removeBossBar();
                } else {
                    boss.tickBossBar();
                }
                //plugin.getLogger().info(boss.mob.getName()+": ticks boss bar");
            }
            bosses.removeIf(BaseBoss::isBossDead);
        }, 0L, 5L);
    }

    public static void stop() {
        for(BukkitTask task : task) {
            if(task!=null) {
                task.cancel();
            }
        }

    }


    public static void add(BaseBoss boss) {
        bosses.add(boss);
    }
}
