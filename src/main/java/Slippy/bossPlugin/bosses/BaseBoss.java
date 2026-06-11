package Slippy.bossPlugin.bosses;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Mob;
import org.bukkit.World;

public class BaseBoss {
    // Base boss class, contains methods to tick abilities.

    protected World world;
    protected Mob mob;
    protected Location loc;
    protected int maxHealth;
    protected int damage;
    protected String name;
    protected int baseCooldown;
    protected int specialCooldown;

    protected ArrayList abilities = new ArrayList();

    public BaseBoss(World world, Location loc) {
        this.world = world;
        this.loc = loc;
    }

    public BaseBoss() {}

    public void spawnBoss() {

    }

    public void tickAbilities() {
        
    }

    public void tick() {
        if(mob==null) return;
        if(mob.isDead()) return;

        tickAbilities();
    }
    public boolean isBossDead() {
        return mob.isDead();
    }

    public String getName() {
        return name;
    }
}
