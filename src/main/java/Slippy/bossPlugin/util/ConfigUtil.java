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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
                // Convert boss config data to map
                Map<String, Object> bossData = (Map<String, Object>) bossEntry;

                // Values are read from config and filled with placeholders if not found
                String name = bossData.containsKey("name") ? (String) bossData.get("name") : "Unnamed Boss";
                String worldString = bossData.containsKey("world") ? (String) bossData.get("world") : "world";
                Map<String, Object> spawnLoc = (Map<String, Object>) bossData.get("spawnLocation");
                double spawnX = spawnLoc.containsKey("x") ? (double) spawnLoc.get("x") : 0;
                double spawnY = spawnLoc.containsKey("y") ? (double) spawnLoc.get("y") : 80;
                double spawnZ = spawnLoc.containsKey("z") ? (double) spawnLoc.get("z") : 0;
                String mob = bossData.containsKey("mob") ? (String) bossData.get("mob") : "ZOMBIE";

                // Load and check attributes
                ArrayList<LinkedHashMap> attributesData;
                if(bossData.containsKey("attributes")) {
                    attributesData = (ArrayList<LinkedHashMap>) bossData.get("attributes");
                    attributesData.forEach(entry -> {
                        if(!entry.containsKey("attribute")||!entry.containsKey("value")) {
                            attributesData.remove(entry);
                        }
                    });
                } else {
                    attributesData = new ArrayList<LinkedHashMap>();
                }

                // Values are applied to create boss
                World world = Bukkit.getWorld(worldString);
                if(world!=null) {
                    CustomBoss boss = new CustomBoss(world, new Location(world, spawnX, spawnY, spawnZ), EntityType.valueOf(mob));

                    // Boss object is given values to apply to itself upon spawning
                    boss.setName(name);
                    if(!attributesData.isEmpty()) {
                        boss.setAttributes(attributesData);
                    }
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
