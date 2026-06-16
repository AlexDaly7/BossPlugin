package Slippy.bossPlugin.bosses;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CustomBoss extends BaseBoss {

    private EntityType entityType;

    public CustomBoss(World world, Location loc, EntityType entityType) {
        this.world = world;
        this.spawnLoc = loc;
        this.entityType = entityType;
    }

    public void spawnBoss() {
        mob = (Mob) world.spawnEntity(spawnLoc, entityType, CreatureSpawnEvent.SpawnReason.DEFAULT, entity -> {
            entity.setCustomName(name);
            entity.setCustomNameVisible(true);
        });
    }
}
