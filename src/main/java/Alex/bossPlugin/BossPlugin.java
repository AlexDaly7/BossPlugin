package Alex.bossPlugin;

import Alex.bossPlugin.bosses.BossManager;
import Alex.bossPlugin.commands.BossPluginCommand;
import Alex.bossPlugin.listeners.BossDeathListener;
import Alex.bossPlugin.listeners.EditorChatListener;
import Alex.bossPlugin.listeners.MenuClickListener;
import Alex.bossPlugin.listeners.MenuCloseListener;
import Alex.bossPlugin.config.ConfigUtil;
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

        // Load listeners
        Bukkit.getPluginManager().registerEvents(new BossDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new EditorChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuCloseListener(), this);
    }

    @Override
    public void onDisable() {
        BossManager.stop();
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}
