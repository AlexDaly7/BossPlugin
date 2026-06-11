package Slippy.bossPlugin.bosses;

import java.util.ArrayList;

import Slippy.bossPlugin.BossPlugin;
import Slippy.bossPlugin.abilities.Ability;
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
    protected int maxBaseCooldown = 10;
    protected int specialCooldown = 20;
    protected int maxSpecialCooldown;

    protected ArrayList<Ability> baseAbilities = new ArrayList();
    protected ArrayList<Ability> specialAbilities = new ArrayList();

    public BaseBoss(World world, Location loc) {
        this.world = world;
        this.loc = loc;
    }

    public BaseBoss() {}

    public void spawnBoss() {

    }

    public void tickAbilities() {

        if(!specialAbilities.isEmpty()&&maxSpecialCooldown!=0) {
            specialCooldown--;
            if(specialCooldown<=0) {
                specialCooldown = maxSpecialCooldown;
                specialAbilities.get((int)(Math.random()*specialAbilities.size())).activate(mob);
                return;
            }
        }
        // Special ability should run over base ability and so skips a tick for base cooldown if called.
        // This gives the player an extra second before a base ability after a special ability, which is beneficial.
        if(!baseAbilities.isEmpty()&&maxBaseCooldown!=0) {
            baseCooldown--;
            if (baseCooldown<=0) {
                baseCooldown = maxBaseCooldown;
                baseAbilities.get((int)(Math.random()*baseAbilities.size())).activate(mob);

            }
        }
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
