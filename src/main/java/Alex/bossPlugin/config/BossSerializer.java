package Alex.bossPlugin.config;

import Alex.bossPlugin.bosses.BaseBoss;
import Alex.bossPlugin.bosses.BossManager;
import Alex.bossPlugin.bosses.Phase;

import java.util.List;
import java.util.Map;

public class BossSerializer {

    public static Map<String, Object> serializeBoss(BaseBoss boss) {
        Map<String, Object> map = Map.of(
            "name", boss.getName()!=null ? boss.getName() : "Un-named boss",
            "world", boss.getSpawnLoc().getWorld(),
            "spawnLocation", Map.of(
                "x", boss.getSpawnLoc().getX(),
                "y", boss.getSpawnLoc().getY(),
                "z", boss.getSpawnLoc().getZ()
            ),
            "health", boss.getHealth(),
            "respawnTimer", boss.getRespawnTimer()
        );

        if(boss.getId()!=null) {
            map.put("id", boss.getId());
        } else {
            map.put("id", BossManager.getBosses().size());
        }

        List<Map<String, Object>> phaseList = List.of();
        for(Phase phase : boss.getPhases()) {
            Map<String, Object> phaseMap = serializePhase(phase);
            if(phaseMap!=null) {
                phaseList.add(phaseMap);
            }
        }

    }

    public static Map<String, Object> serializePhase(Phase phase) {

    }
}
