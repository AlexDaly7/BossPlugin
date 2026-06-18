package Slippy.bossPlugin.bosses;

import Slippy.bossPlugin.abilities.AbilityType;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Spider;

public class EvilSpider extends BaseBoss {
    public EvilSpider(World world, Location loc) {
        this.world = world;
        mob = (Spider) world.spawnEntity(loc, EntityType.SPIDER);
        mob.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(3.5);
        mob.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3);
        mob.setCustomName("Big Evil Spider");
        mob.setCustomNameVisible(true);
    }

    //@Override
    //public void startAbilityTask() {

    //}
}
