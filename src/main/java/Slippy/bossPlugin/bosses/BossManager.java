package Slippy.bossPlugin.bosses;

import Slippy.bossPlugin.BossPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class BossManager {
    static BukkitTask[] task = new BukkitTask[2];
    static JavaPlugin plugin = BossPlugin.getPlugin();
    static ArrayList<BaseBoss> bosses = new ArrayList<>();

    public static void start() {
        // Task that ticks abilities, runs every second
        task[0] = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for(BaseBoss boss : bosses) {
                boss.tickAbilities();
            }
        }, 0L, 20L);

        // Task that ticks boss bar, removes bosses from arraylist once dead etc...
        // Runs 4 times a second.
        task[1] = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
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
        task[0].cancel();
        task[1].cancel();
    }


    public static void add(BaseBoss boss) {
        bosses.add(boss);
    }
}
