package Slippy.bossPlugin.bosses;

import Slippy.bossPlugin.BossPlugin;
import Slippy.bossPlugin.abilities.AbilityType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

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


