package Slippy.bossPlugin.bosses;

import Slippy.bossPlugin.BossPlugin;
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
        applyAttributes();
    }

    public void setAttributes(List<Map<String, Object>> attributes) {
        this.attributes = attributes;
    }

    public void applyAttributes() {
        attributes.forEach(entry -> {
            mob.getAttribute((Attribute) entry.get("attribute")).setBaseValue((double) entry.get("value"));

        });
    }

    //public void setPhases(ArrayList<> phases) {

    //}
}
