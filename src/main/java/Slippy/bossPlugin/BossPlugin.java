package Slippy.bossPlugin;

import Slippy.bossPlugin.bosses.BossManager;
import Slippy.bossPlugin.commands.BossPluginCommand;
import Slippy.bossPlugin.util.ConfigUtil;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.Bukkit;
import org.bukkit.World;

public final class BossPlugin extends JavaPlugin {
    static JavaPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        ConfigUtil.createConfig();
        World world = Bukkit.getWorld("world");

        getCommand("BossPlugin").setExecutor(new BossPluginCommand());

        //BaseBoss spider = new EvilSpider(world, new Location(world, 200, 83, 200));
        //BossManager.add(spider);
        BossManager.loadBosses(ConfigUtil.getBosses());
        BossManager.start();

    }

    @Override
    public void onDisable() {
        BossManager.stop();
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}
