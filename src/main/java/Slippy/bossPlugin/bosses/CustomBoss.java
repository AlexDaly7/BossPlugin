package Slippy.bossPlugin.bosses;

import Slippy.bossPlugin.BossPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class CustomBoss extends BaseBoss {

    private EntityType entityType;
    private List<Map<String, Object>> attributes;

    public CustomBoss(World world, Location loc) {
        this.world = world;
        this.spawnLoc = loc;
    }

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
        mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        mob.setHealth(maxHealth);
        mob.addScoreboardTag("boss");
        mob.addScoreboardTag(name);
        applyAttributes();
    }

    public void setAttributes(List<Map<String, Object>> attributes) {
        this.attributes = attributes;
    }

    public void applyAttributes() {
        if (attributes!=null) {
            attributes.forEach(entry -> {
                mob.getAttribute((Attribute) entry.get("attribute")).setBaseValue(((Number) entry.get("value")).doubleValue());
            });
        }
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public void addPhase(Phase phase) {
        phases.add(phase);
    }

    public Phase getPhase(int i) {
        return phases.get(i);
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public Location getSpawnLoc() {
        return spawnLoc;
    }

    public void setSpawnLoc(Location spawnLoc) {
        this.spawnLoc = spawnLoc;
    }
}
