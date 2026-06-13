package Slippy.bossPlugin;

import Slippy.bossPlugin.bosses.BossManager;
import Slippy.bossPlugin.bosses.BaseBoss;
import Slippy.bossPlugin.bosses.EvilSpider;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.Bukkit;
import org.bukkit.World;


public final class BossPlugin extends JavaPlugin {
    static JavaPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        World world = Bukkit.getWorld("world");

        BaseBoss spider = new EvilSpider(world, new Location(world, 200, 83, 200));
        BossManager.add(spider);
        BossManager.start();
    }

    @Override
    public void onDisable() {

    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}
