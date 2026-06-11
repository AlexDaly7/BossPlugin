package Slippy.bossPlugin.util;

import Slippy.bossPlugin.BossPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Boss;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class TaskUtil {

    public static void runTimedTask(Runnable func, int delay, int interval, int iterations) {
        JavaPlugin plugin = BossPlugin.getPlugin();
        BukkitTask[] task = new BukkitTask[1];
        int[] count = new int[1];

        task[0] = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (count[0]>=iterations) {
                task[0].cancel();
            }
            func.run();
            count[0]++;
        }, delay, interval);
    }
}
