package Slippy.bossPlugin.bosses;

import Slippy.bossPlugin.BossPlugin;
import Slippy.bossPlugin.abilities.AbilityType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Spider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;


public class EvilSpider extends BaseBoss {
    public EvilSpider(World world, Location loc) {
        this.world = world;
        this.loc = loc;
        mob = (Spider) world.spawnEntity(loc, EntityType.SPIDER);
        mob.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(2);
        mob.setCustomName("Big Evil Spider");
        mob.setCustomNameVisible(true);
        mob.setAI(false);
    }

    @Override
    public void tickAbilities() {
        AbilityType.EXPLOSION.activate(mob, 100);
    }

    //@Override
    //public void startAbilityTask() {

    //}
}
