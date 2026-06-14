package Slippy.bossPlugin.util;

import Slippy.bossPlugin.BossPlugin;
import Slippy.bossPlugin.bosses.BaseBoss;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigUtil {

    static JavaPlugin plugin;
    static File file;
    static FileConfiguration fileConfig;

    public static void createConfig() {
        plugin = BossPlugin.getPlugin();
        file = new File(plugin.getDataFolder(), "bosses.yml");
        if(!file.exists()) {
            plugin.saveResource("bosses.yml", false);
        }
        fileConfig = YamlConfiguration.loadConfiguration(file);
    }

    public static ArrayList<BaseBoss> loadBosses() {
        List bossList = fileConfig.getList("bosses");
        if(bossList!=null) {
            plugin.getLogger().info("Bosses detected");
            for(Object boss : bossList) {
                
            }
        } else {
            plugin.getLogger().info("no bosses");
        }

        return new ArrayList<>();

    }
}
