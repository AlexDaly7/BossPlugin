package Alex.bossPlugin.config;

import Alex.bossPlugin.BossPlugin;
import Alex.bossPlugin.bosses.BaseBoss;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
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
        ArrayList<BaseBoss> idBosses = new ArrayList<>();

        List bossList = fileConfig.getList("bosses");
        if(bossList!=null) {
            plugin.getLogger().info("Bosses detected");
            for(int i=0;i<bossList.size();i++) {
                try {
                    BaseBoss boss = BossParser.parseBoss((Map<String, Object>) bossList.get(i));
                    if(boss.getId()!=null) {
                        bosses.add(boss);
                    } else {
                        idBosses.add(boss);
                    }
                } catch (Exception e) {
                    plugin.getLogger().warning("Boss "+(i+1)+" info could not be loaded from config.\n"+e);
                }
            }
            addBossIds(bossList, bosses, idBosses);
        } else {
            plugin.getLogger().info("no bosses");
        }
        return bosses;
    }

    public static void addBossIds(List bossList, ArrayList<BaseBoss> bosses, ArrayList<BaseBoss> idBosses) {
        if(!idBosses.isEmpty()) {
            for (BaseBoss idBoss : idBosses) {
                idBoss.setId(bosses.size());
                bosses.add(idBoss);
                for(Object bossEntry : bossList) {
                    Map<String, Object> boss = (Map<String, Object>) bossEntry;
                    if(idBoss.getName().equals(boss.get("name"))) {
                        boss.put("id", idBoss.getId());
                    }
                }
            }
            fileConfig.set("bosses", bossList);
            try {
                fileConfig.save(file);
            } catch(IOException e) {
                plugin.getLogger().warning(e.toString());
            }

        }
    }

    public static void saveBoss(BaseBoss boss) {
        List<Map<String, Object>> bosses = (List<Map<String, Object>>) fileConfig.getList("bosses");
        boolean bossExists = false;
        int bossIndex = 0;
        for(int i=0;i<bosses.size();i++) {
            if(bossExists) return;
            if(boss.getId().equals(bosses.get(i).get("id"))) {
                bossExists = true;
                bossIndex = i;
            }
        }
        if(bossExists) {
            bosses.remove(bossIndex);
            bosses.add(bossIndex, BossSerializer.serializeBoss(boss));
        } else {
            bosses.add(BossSerializer.serializeBoss(boss));
        }
        fileConfig.set("bosses", bosses);
        try {
            fileConfig.save(file);
        } catch(IOException e) {
            plugin.getLogger().warning(e.toString());
        }
    }

}
