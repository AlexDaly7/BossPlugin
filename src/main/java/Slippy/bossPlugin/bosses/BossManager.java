package Slippy.bossPlugin.bosses;

import Slippy.bossPlugin.BossPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class BossManager {
    static BukkitTask[] task = new BukkitTask[1];
    static JavaPlugin plugin = BossPlugin.getPlugin();
    static ArrayList<CustomBoss> bosses = new ArrayList<>();

    public static void start() {
        task[0] = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for(CustomBoss boss : bosses) {
                boss.tick();
                plugin.getLogger().info("Boss: "+boss.getName());
            }
        }, 0L, 100L);
    }

    public static void stop() {
        task[0].cancel();
    }


    public static void add(CustomBoss boss) {
        bosses.add(boss);
    }
}
