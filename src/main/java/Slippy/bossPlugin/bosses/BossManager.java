package Slippy.bossPlugin.bosses;

import Slippy.bossPlugin.BossPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class BossManager {
    static BukkitTask[] task = new BukkitTask[1];
    static JavaPlugin plugin = BossPlugin.getPlugin();
    static ArrayList<BaseBoss> bosses = new ArrayList<>();

    public static void start() {
        task[0] = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for(BaseBoss boss : bosses) {
                boss.tick();
                plugin.getLogger().info("Boss: "+boss.mob.getName());
            }
        }, 0L, 20L);
    }

    public static void stop() {
        task[0].cancel();
    }


    public static void add(BaseBoss boss) {
        bosses.add(boss);
    }
}
