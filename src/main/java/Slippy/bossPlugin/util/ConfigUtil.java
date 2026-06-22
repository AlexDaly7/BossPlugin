package Slippy.bossPlugin.util;

import Slippy.bossPlugin.BossPlugin;
import Slippy.bossPlugin.abilities.Ability;
import Slippy.bossPlugin.abilities.AbilityType;
import Slippy.bossPlugin.bosses.BaseBoss;
import Slippy.bossPlugin.bosses.CustomBoss;
import Slippy.bossPlugin.bosses.Phase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
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
        // TODO: proper feedback using Bukkit.broadcastMessage() to inform user of yml mistakes

        ArrayList<BaseBoss> bosses = new ArrayList<BaseBoss>();
        List bossList = fileConfig.getList("bosses");
        if(bossList!=null) {
            plugin.getLogger().info("Bosses detected");
            for(Object bossEntry : bossList) {
                // Convert boss config data to map
                Map<String, Object> bossData = (Map<String, Object>) bossEntry;

                // Values are read from config and filled with placeholders if not found
                String name = bossData.containsKey("name") ? (String) bossData.get("name") : "Unnamed Boss";
                int health = bossData.containsKey("health") ? (int) bossData.get("health") : 100;
                int respawnTimer = bossData.containsKey("respawnTimer") ? (int) bossData.get("respawnTimer") : 500;
                String worldString = bossData.containsKey("world") ? (String) bossData.get("world") : "world";
                Map<String, Object> spawnLoc = (Map<String, Object>) bossData.get("spawnLocation");
                double spawnX = spawnLoc.containsKey("x") ? ((Number) spawnLoc.get("x")).doubleValue() : 0;
                double spawnY = spawnLoc.containsKey("y") ? ((Number) spawnLoc.get("y")).doubleValue() : 80;
                double spawnZ = spawnLoc.containsKey("z") ? ((Number) spawnLoc.get("z")).doubleValue() : 0;
                String mob = bossData.containsKey("mob") ? (String) bossData.get("mob") : "ZOMBIE";


                // Load and parse attributes
                List<Map<String, Object>> parsedAttributes = new ArrayList<>();
                if(bossData.containsKey("attributes")) {
                    for(Object attribute : (List) bossData.get("attributes")) {
                        Map<String, Object> parsed = parseAttribute((Map<String, Object>) attribute);
                        if(parsed!=null) {
                            parsedAttributes.add(parsed);
                        }
                    }
                }

                // Load and parse phases
                List<Phase> phases = new ArrayList<Phase>();
                List<Map<String, Object>> phasesData = (ArrayList) bossData.get("phases");
                if(!phasesData.isEmpty()) {
                    for(Map<String, Object> phase : phasesData) {
                        Phase parsed = parsePhase(phase);
                        if(parsed!=null) {
                            phases.add(parsed);

                        }
                    }
                }

                // Values are applied to create boss
                World world = Bukkit.getWorld(worldString);
                if(world!=null) {
                    CustomBoss boss = new CustomBoss(world, new Location(world, spawnX, spawnY, spawnZ), EntityType.valueOf(mob));

                    // Boss object is given values to apply to itself upon spawning
                    boss.setName(name);
                    boss.createBossBar();
                    boss.setHealth(health);
                    boss.setRespawnTimer(respawnTimer);
                    if(!phases.isEmpty()) {
                        boss.setPhases(phases);
                    }

                    if(!parsedAttributes.isEmpty()) {
                        boss.setAttributes(parsedAttributes);
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

    public static Map<String, Object> parseAttribute(Map<String, Object> attributeData) {
        if(!attributeData.containsKey("attribute")||!attributeData.containsKey("value")) {
            return null;
        }
        try {
            // Get attribute object from string, catch if invalid.
            Attribute attribute = Attribute.valueOf(attributeData.get("attribute").toString());
            return Map.of("attribute", attribute, "value", attributeData.get("value"));
        } catch(IllegalArgumentException e) {
            BossPlugin.getPlugin().getLogger().warning("Attribute "+attributeData.get("attribute")+" could not be found.");
            return null;
        }
    }

    public static Phase parsePhase(Map<String, Object> phaseData) {
        List<Ability> specialAbilities = new ArrayList<Ability>();
        List<Ability> baseAbilities = new ArrayList<Ability>();
        Phase phase;
        if(!phaseData.containsKey("health")||(double)phaseData.get("health")>1) {
            return null;
        }
        // If there is not at least one ability list return null
        if(
            (!phaseData.containsKey("specialAbilities")||!phaseData.containsKey("specialCooldown"))
            &&
            (!phaseData.containsKey("baseAbilities")||!phaseData.containsKey("baseCooldown"))
        ) {
            return null;
        } else {
            phase = new Phase((double) phaseData.get("health"), (int) phaseData.get("baseCooldown"), (int) phaseData.get("specialCooldown"));
            if(phaseData.containsKey("particles")) {
               phase.setParticle(parseParticle((Map<String, Object>) phaseData.get("particles")));
            } else {
                phase.setParticle(Particle.GLOW);
            }

            if (phaseData.containsKey("specialAbilities")) {
                ArrayList<Map<String, Object>> abilities = (ArrayList) phaseData.get("specialAbilities");
                if (!abilities.isEmpty()) {
                    for (Map<String, Object> ability : abilities) {
                        Ability parsed = parseAbility(ability);
                        if (parsed != null) {
                            specialAbilities.add(parsed);
                        }
                    }
                    phase.setSpecialAbilities(specialAbilities);
                }
            }
            if (phaseData.containsKey("baseAbilities")) {
                ArrayList<Map<String, Object>> abilities = (ArrayList) phaseData.get("baseAbilities");
                if (!abilities.isEmpty()) {
                    for (Map<String, Object> ability : abilities) {
                        Ability parsed = parseAbility(ability);
                        if (parsed != null) {
                            baseAbilities.add(parsed);
                        }
                    }
                    phase.setBaseAbilities(baseAbilities);
                }
            }
            return phase;
        }
    }

    public static Ability parseAbility(Map<String, Object> abilityData) {
        if(!abilityData.containsKey("ability")) {
            return null;
        }
        int range = abilityData.containsKey("range") ? (int) abilityData.get("range") : 50;
        AbilityType ability;
        try {
            ability = AbilityType.valueOf((String) abilityData.get("ability"));
            return ability.create(range);
        } catch(IllegalArgumentException e) {
            plugin.getLogger().info(abilityData.get("ability")+" is not a valid ability.");
            return null;
        }
    }

    public static Particle parseParticle(Map<String, Object> particleData) {
        if(!particleData.containsKey("particle")) {
            return null;
        }
        //int range = particleData.containsKey("range") ? (int) abilityData.get("range") : 50;
        try {
            return Particle.valueOf((String) particleData.get("particle"));
        } catch(IllegalArgumentException e) {
            plugin.getLogger().info(particleData.get("ability")+" is not a valid particle.");
            return null;
        }
    }
}
