package Slippy.bossPlugin.bosses;

import Slippy.bossPlugin.BossPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CustomBoss extends BaseBoss {

    private EntityType entityType;
    private ArrayList<LinkedHashMap> attributes;

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
        applyAttributes();
    }

    public void setAttributes(ArrayList<LinkedHashMap> attributes) {
        this.attributes = attributes;
    }

    public void applyAttributes() {
        attributes.forEach(entry -> {
            try {
                Attribute attribute = Attribute.valueOf(entry.get("attribute").toString());
                if (attribute != null) {
                    mob.getAttribute(attribute).setBaseValue((double) entry.get("value"));
                }
            } catch(IllegalArgumentException e) {
                BossPlugin.getPlugin().getLogger().warning("Attribute "+entry.get("attribute")+" could not be found.");
                BossPlugin.getPlugin().getLogger().warning(entry.lastEntry().toString());
            }

        });
    }
}
