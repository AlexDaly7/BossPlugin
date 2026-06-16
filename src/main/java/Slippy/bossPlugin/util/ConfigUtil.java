package Slippy.bossPlugin.util;

import Slippy.bossPlugin.BossPlugin;
import Slippy.bossPlugin.bosses.BaseBoss;
import Slippy.bossPlugin.bosses.CustomBoss;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
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

    public static ArrayList<BaseBoss> getBosses() {
        ArrayList<BaseBoss> bosses = new ArrayList<BaseBoss>();
        List bossList = fileConfig.getList("bosses");
        if(bossList!=null) {
            plugin.getLogger().info("Bosses detected");
            for(Object bossEntry : bossList) {
                Map<String, Object> bossData = (Map<String, Object>) bossEntry;
                String name = bossData.containsKey("name") ? (String) bossData.get("boss.name") : "Unnamed Boss";
                String worldString = bossData.containsKey("world") ? (String) bossData.get("world") : "world";
                double spawnX = bossData.containsKey("spawnLocation.x") ? (double) bossData.get("spawnLocation.x") : 0;
                double spawnY = bossData.containsKey("spawnLocation.y") ? (double) bossData.get("spawnLocation.y") : 80;
                double spawnZ = bossData.containsKey("spawnLocation.z") ? (double) bossData.get("spawnLocation.z") : 0;
                String mob = bossData.containsKey("mob") ? (String) bossData.get("mob") : "ZOMBIE";
                World world = Bukkit.getWorld(worldString);
                if(world!=null) {
                    BaseBoss boss = new CustomBoss(world, new Location(world, spawnX, spawnY, spawnZ), EntityType.valueOf(mob));
                    boss.setName(name);

                    bosses.add(boss);

                } else {
                    plugin.getLogger().info(worldString+" is not a valid world.");
                }

            }
        } else {
            plugin.getLogger().info("no bosses");
        }

        return bosses;

    }
}
