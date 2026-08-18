package Alex.bossPlugin.bosses;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Spider;

public class HugeBoss extends BaseBoss {
    // Boss with different objectives to complete (ie, pylons to destroy before boss can be attacked)

    public HugeBoss(World world, Location loc) {
        this.world = world;
        this.spawnLoc = loc;
    }
    @Override
    public void spawnBoss() {
        mob = (Spider) world.spawnEntity(spawnLoc, EntityType.SPIDER);
        mob.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(2);
        mob.setCustomName("Big Huge Boss");
        mob.setCustomNameVisible(true);
        //mob.setHealth(maxHealth);
        mob.setAI(false);
    }
}


