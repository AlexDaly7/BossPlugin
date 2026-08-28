package Alex.bossPlugin.config;

import Alex.bossPlugin.BossPlugin;
import Alex.bossPlugin.abilities.Ability;
import Alex.bossPlugin.abilities.AbilityType;
import Alex.bossPlugin.bosses.BaseBoss;
import Alex.bossPlugin.bosses.CustomBoss;
import Alex.bossPlugin.bosses.Phase;
import Alex.bossPlugin.passiveEffects.PassiveEffect;
import Alex.bossPlugin.passiveEffects.PassiveEffectType;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BossParser {
    private static JavaPlugin plugin = null;

    private static void getPlugin() {
        plugin = BossPlugin.getPlugin();
    }

    public static BaseBoss parseBoss(Map<String, Object> bossData) {
        if(plugin==null) {
            getPlugin();
        }
        // TODO: proper feedback using Bukkit.broadcastMessage() to inform user of yml mistakes

        // Values are read from config and filled with placeholders if not found
        String name = bossData.containsKey("name") ? (String) bossData.get("name") : "Unnamed Boss";
        int health = bossData.containsKey("health") ? (int) bossData.get("health") : 100;
        int respawnTimer = bossData.containsKey("respawnTimer") ? (int) bossData.get("respawnTimer") : 500;
        Map<String, Object> spawnLoc = bossData.containsKey("spawnLocation") ?
                (Map<String, Object>) bossData.get("spawnLocation") :
                Map.of(
                    "x", 0,
                    "y", 80,
                    "z", 0
                );
        double spawnX = ((Number) spawnLoc.get("x")).doubleValue();
        double spawnY = ((Number) spawnLoc.get("y")).doubleValue();
        double spawnZ = ((Number) spawnLoc.get("z")).doubleValue();
        String mob = bossData.containsKey("mob") ? (String) bossData.get("mob") : "ZOMBIE";


        // Load and parse attributes
        List<Map<String, Object>> parsedAttributes = new ArrayList<>();
        if(bossData.containsKey("attributes")) {
            if(bossData.get("attributes")!=null) {
                for (Object attribute : (List) bossData.get("attributes")) {
                    Map<String, Object> parsed = parseAttribute((Map<String, Object>) attribute);
                    if (parsed != null) {
                        parsedAttributes.add(parsed);
                    }
                }
            }
        }

        // Load and parse phases
        List<Phase> phases = new ArrayList<Phase>();
        List<Map<String, Object>> phasesData = (ArrayList) bossData.get("phases");
        if(phasesData!=null) {
            if (!phasesData.isEmpty()) {
                for (Map<String, Object> phase : phasesData) {
                    Phase parsed = parsePhase(phase);
                    if (parsed != null) {
                        phases.add(parsed);

                    }
                }
            }
        }

        // Load and parse loottable items
        List<Map<String, Object>> items = new ArrayList<>();
        List<Map<String, Object>> lootData = (ArrayList) bossData.get("loottable");
        if(lootData!=null) {
            if (!lootData.isEmpty()) {
                for (Map<String, Object> item : lootData) {
                    Map<String, Object> parsed = parseLoottable(item);
                    if (parsed != null) {
                        items.add(parsed);
                    }
                }
            }
        }

        World world = parseWorld((String) bossData.get("world"));

        // Values are applied to create boss
        CustomBoss boss = new CustomBoss(world, new Location(world, spawnX, spawnY, spawnZ), EntityType.valueOf(mob));

        // Boss object is given values to apply to itself upon spawning
        boss.setName(name);
        boss.createBossBar();
        boss.setHealth(health);
        boss.setRespawnTimer(respawnTimer);
        boss.setLootList(items);
        if(!phases.isEmpty()) {
            boss.setPhases(phases);
        } else {
            plugin.getLogger().info("No phases (or abilities) loaded for "+name);
        }

        if(!parsedAttributes.isEmpty()) {
            boss.setAttributes(parsedAttributes);
        } else {
            plugin.getLogger().info("No attributes loaded for "+name);
        }

        // Parse id
        boss.setId(parseId(bossData.get("id")));

        return boss;
    }

    public static Integer parseId(Object idData) {
        if(idData==null) return null;
        try {
            return Integer.parseInt(idData.toString());
        } catch(NumberFormatException e) {
            plugin.getLogger().warning("Id for '"+idData+"' is invalid. Assigning automatic id.");
            return null;
        }
    }

    public static World parseWorld(String input) {
        World world = Bukkit.getWorld(input);
        if(world==null) {
            plugin.getLogger().warning("World '"+input+"' does not exist, "+Bukkit.getWorlds().getFirst().toString()+" has been set instead.");
            return Bukkit.getWorlds().getFirst();
        } else {
            return world;
        }
    }

    public static Map<String, Object> parseAttribute(Map<String, Object> attributeData) {
        if(!attributeData.containsKey("attribute")||!attributeData.containsKey("value")) {
            plugin.getLogger().info("Attribute field must contain an attribute and a value.");
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
            plugin.getLogger().info("Phase must contain a health value under 1 to represent the percentage of health to change to this phase at.");
            return null;
        }
        // If there is not at least one ability list return null
        if(
                (!phaseData.containsKey("specialAbilities")||!phaseData.containsKey("specialCooldown"))
                        &&
                        (!phaseData.containsKey("baseAbilities")||!phaseData.containsKey("baseCooldown"))
        ) {
            plugin.getLogger().info("Phase must contain either base or special abilities");
            return null;
        } else {
            // Parse time and particles for transition
            phase = new Phase((double) phaseData.get("health"), (int) phaseData.get("baseCooldown"), (int) phaseData.get("specialCooldown"));
            if(phaseData.containsKey("transition")) {
                Map<String, Object> transData = (Map<String, Object>) phaseData.get("transition");
                if(transData.containsKey("time")) {
                    phase.setTransitionTime(((Number) transData.get("time")).doubleValue());
                }
                if(transData.containsKey("particles")) {
                    phase.setParticle(parseParticle((Map<String, Object>) transData.get("particles")));
                } else {
                    phase.setParticle(Particle.GLOW);
                }
            }

            // Parse array of special abilities
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

            // Parse array of base abilities
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

            // Parse array of effects
            if(phaseData.containsKey("effects")) {
                for (Map<String, Object> effect : (ArrayList<Map<String, Object>>) phaseData.get("effects")) {
                    PassiveEffect parsedEffect = parseEffect(effect);
                    if (parsedEffect != null) {
                        phase.addEffect(parsedEffect);
                    }
                }
            }
            return phase;
        }
    }

    public static PassiveEffect parseEffect(Map<String, Object> effectData) {
        try {
            return PassiveEffectType.valueOf((String) effectData.get("effect")).create(effectData);
        } catch(IllegalArgumentException e) {
            plugin.getLogger().warning(effectData.get("effect")+" is not a valid effect.");
            return null;
        }
    }

    public static Ability parseAbility(Map<String, Object> abilityData) {
        if(!abilityData.containsKey("ability")) {
            plugin.getLogger().info("Ability list must contain ability specification.");
            return null;
        }
        AbilityType ability;
        try {
            ability = AbilityType.valueOf((String) abilityData.get("ability"));
            return ability.create(abilityData);
        } catch(IllegalArgumentException e) {
            plugin.getLogger().info(abilityData.get("ability")+" is not a valid ability.");
            return null;
        }
    }

    public static Particle parseParticle(Map<String, Object> particleData) {
        if(!particleData.containsKey("particle")) {
            plugin.getLogger().info("Particle type must be present.");
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

    public static Map<String, Object> parseLoottable(Map<String, Object> lootData) {
        if(!lootData.containsKey("item")&&lootData.containsKey("amount")) return null;
        if((int)lootData.get("amount")<=0) return null;

        try {
            Material material = Material.valueOf((String) lootData.get("item"));
            if(lootData.containsKey("chance")) {
                return Map.of("item", material, "amount", lootData.get("amount"), "chance", lootData.get("chance"));
            } else {
                return Map.of("item", material, "amount", lootData.get("amount"));
            }
        } catch (IllegalArgumentException e) {
            plugin.getLogger().info("Item "+lootData.get("item")+" is not a valid item.");
            return null;
        }
    }
}
