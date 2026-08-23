package Alex.bossPlugin.config;

import Alex.bossPlugin.abilities.Ability;
import Alex.bossPlugin.bosses.BaseBoss;
import Alex.bossPlugin.bosses.BossManager;
import Alex.bossPlugin.bosses.CustomBoss;
import Alex.bossPlugin.bosses.Phase;
import Alex.bossPlugin.passiveEffects.PassiveEffect;
import Alex.bossPlugin.passiveEffects.PassiveEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BossSerializer {

    public static Map<String, Object> serializeBoss(BaseBoss boss) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", boss.getName()!=null ? boss.getName() : "Un-named boss");
        map.put("world", boss.getSpawnLoc().getWorld());
        map.put("spawnLocation", Map.of(
                "x", boss.getSpawnLoc().getX(),
                "y", boss.getSpawnLoc().getY(),
                "z", boss.getSpawnLoc().getZ()
            ));
        map.put("health", boss.getHealth());
        map.put("respawnTimer", boss.getRespawnTimer());

        if(boss.getId()!=null) {
            map.put("id", boss.getId());
        } else {
            map.put("id", BossManager.getBosses().size());
        }

        List<Map<String, Object>> phaseList = new ArrayList<>();
        for(Phase phase : boss.getPhases()) {
            Map<String, Object> phaseMap = serializePhase(phase);
            if(!phaseMap.isEmpty()) {
                phaseList.add(phaseMap);
            }
        }

        List<Map<String, Object>> lootTable = new ArrayList<>();
        for(Map<String, Object> loot : boss.getLootList()) {
            lootTable.add(serializeLoot(loot));
        }
        map.put("loottable", lootTable);

        if(boss instanceof CustomBoss) {
            List<Map<String, Object>> attributes = new ArrayList<>();
            for(Map<String, Object> attribute : ((CustomBoss) boss).getAttributes()) {
                Map<String, Object> attributeMap = serializeAttribute(attribute);
                if(attributeMap!=null) {
                    attributes.add(attributeMap);
                }
            }
            map.put("attributes", attributes);
        }


        return map;
    }

    public static Map<String, Object> serializePhase(Phase phase) {
        Map<String, Object> phaseMap = new HashMap<>();

        phaseMap.put("health", phase.getMaxHealthRange());
        phaseMap.put("specialCooldown", phase.getMaxSpecialCooldown());
        phaseMap.put("baseCooldown", phase.getMaxBaseCooldown());

        Map<String, Object> transitionMap = new HashMap<>();
        transitionMap.put("time", phase.getTransitionTime());

        if(phase.getParticle()!=null) {
            transitionMap.put("particles",
                Map.of(
                    "particle", phase.getParticle().toString()
                )
            );
        }
        phaseMap.put("transition", transitionMap);

        List<Map<String, Object>> effects = new ArrayList<>();
        if(phase.getEffects()!=null) {
            for(PassiveEffect effect : phase.getEffects()) {
                effects.add(serializeEffect(effect));
            }
            phaseMap.put("effects", effects);
        }

        List<Map<String, Object>> specialAbilities = new ArrayList<>();
        for(Ability ability : phase.getSpecialAbilities()) {
            specialAbilities.add(serializeAbility(ability));
        }
        phaseMap.put("specialAbilities", specialAbilities);

        List<Map<String, Object>> baseAbilities = new ArrayList<>();
        for(Ability ability : phase.getBaseAbilities()) {
            baseAbilities.add(serializeAbility(ability));
        }
        phaseMap.put("baseAbilities", baseAbilities);

        return phaseMap;
    }

    public static Map<String, Object> serializeEffect(PassiveEffect effect) {
        Map<String, Object> effectMap = Map.of(
            "effect", PassiveEffectType.valueOf(effect.getClass().toString()),
            "range", effect.getRange(),
            "amplifier", effect.getAmplifier()
        );

        // Save extra fields from effect.
        Map<String, Object> extraData = effect.getData();
        extraData.forEach((string, object) -> {
            effectMap.put(string, object);
        });

        return effectMap;
    }

    public static Map<String, Object> serializeAbility(Ability ability) {
        Map<String, Object> abilityMap = new HashMap<>();

        // Ability name must be in uppercase with _ between each word.
        abilityMap.put(
            "ability",
            ability.getName().toUpperCase().replace(' ', '_')
        );

        Map<String, Object> savedData = ability.getData();
        savedData.remove("ability");

        savedData.forEach((string, object) -> {
           abilityMap.put(string, object);
        });

        return abilityMap;
    }

    public static Map<String, Object> serializeLoot(Map<String, Object> loot) {
        if(loot.containsKey("item")&&loot.containsKey("amount")) {
            return loot;
        } else {
            return null;
        }
    }

    public static Map<String, Object> serializeAttribute(Map<String, Object> attribute) {
        if(attribute.containsKey("attribute")&&attribute.containsKey("value")) {
            return attribute;
        } else {
            return null;
        }
    }
}