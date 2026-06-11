package Slippy.bossPlugin.bosses;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Mob;

public class CustomBoss extends BaseBoss {

    public CustomBoss(World world, Location loc, Mob mob) {
        this.world = world;
        this.loc = loc;
        this.mob = mob;
    }
}
