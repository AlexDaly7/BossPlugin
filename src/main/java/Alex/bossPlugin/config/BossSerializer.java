package Alex.bossPlugin.config;

import Alex.bossPlugin.abilities.Ability;
import Alex.bossPlugin.bosses.BaseBoss;
import Alex.bossPlugin.bosses.BossManager;
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
            specialAbilities.add(serialiseAbility(ability));
        }
        phaseMap.put("specialAbilities", specialAbilities);

        List<Map<String, Object>> baseAbilities = new ArrayList<>();
        for(Ability ability : phase.getBaseAbilities()) {
            baseAbilities.add(serialiseAbility(ability));
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

    public static Map<String, Object> serialiseAbility(Ability ability) {
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
}