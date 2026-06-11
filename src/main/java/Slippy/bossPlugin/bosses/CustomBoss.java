package Slippy.bossPlugin.bosses;

import java.util.ArrayList;

import Slippy.bossPlugin.BossPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Mob;
import org.bukkit.World;

public class CustomBoss {

    protected World world;
    protected Mob mob;
    protected Location loc;
    protected int maxHealth;
    protected int damage;
    protected String name;

    protected ArrayList abilities = new ArrayList();

    public CustomBoss(World world, Location loc) {
        this.world = world;
        this.loc = loc;
    }

    public CustomBoss(World world, Location loc, Mob mob) {
        this.world = world;
        this.loc = loc;
        this.mob = mob;
    }

    public CustomBoss(World world, Location loc, Mob mob, String name) {
        this.world = world;
        this.loc = loc;
        this.mob = mob;
        this.name = name;
    }

    public CustomBoss() {}

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
