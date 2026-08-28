package Alex.bossPlugin.bosses;

import Alex.bossPlugin.BossPlugin;
import org.apache.commons.lang3.ObjectUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.CreatureSpawnEvent;

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
                try {
                    mob.getAttribute((Attribute) entry.get("attribute")).setBaseValue(((Number) entry.get("value")).doubleValue());
                } catch (NullPointerException e) {
                    BossPlugin.getPlugin().getLogger().warning(entry.get("attribute")+" could not be loaded onto "+name+".");
                }
            });
        }
    }

    public List<Map<String, Object>> getAttributes() {
        return attributes;
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
}
