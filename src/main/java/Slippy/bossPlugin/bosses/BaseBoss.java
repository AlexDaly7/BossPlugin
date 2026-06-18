package Slippy.bossPlugin.bosses;

import java.util.ArrayList;

import Slippy.bossPlugin.BossPlugin;
import Slippy.bossPlugin.abilities.Ability;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Mob;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class BaseBoss {
    // Base boss class, contains methods to tick abilities.

    // Important reference variables
    protected World world;
    protected Mob mob;
    protected Location spawnLoc;

    protected int maxHealth;
    protected int damage;
    protected String name;

    // Ability variables
    protected int baseCooldown = 10;
    protected int specialCooldown = 20;

    // Phase variables
    protected Phase activePhase;
    protected ArrayList<Phase> phases = new ArrayList<Phase>();

    // Boss bar
    protected BossBar bossBar = Bukkit.createBossBar("CRAZY EVIL SPIDER", BarColor.BLUE, BarStyle.SEGMENTED_6);

    public BaseBoss(World world, Location loc) {
        this.world = world;
        this.spawnLoc = loc;
    }

    public BaseBoss() {}

    public void spawnBoss() {}

    public void tickAbilities() {
        // Ensure abilities don't work if mob is not spawned or is dead.
        if(mob==null) return;
        if(mob.isDead()) return;

        if(!activePhase.getSpecialAbilities().isEmpty()&&activePhase.getMaxSpecialCooldown()!=0) {
            specialCooldown--;
            if(specialCooldown<=0) {
                ArrayList<Ability> specialAbilities = activePhase.getSpecialAbilities();
                specialCooldown = activePhase.getMaxSpecialCooldown();
                specialAbilities.get((int)(Math.random()*specialAbilities.size())).activate(mob);
                return;
            }
        }
        // Special ability should run over base ability and so skips a tick for base cooldown if called.
        // This gives the player an extra second before a base ability after a special ability, which is beneficial.
        if(!activePhase.getBaseAbilities().isEmpty()&&activePhase.getMaxBaseCooldown()!=0) {
            baseCooldown--;
            if (baseCooldown<=0) {
                ArrayList<Ability> baseAbilities = activePhase.getBaseAbilities();
                baseCooldown = activePhase.getMaxBaseCooldown();
                baseAbilities.get((int)(Math.random()*baseAbilities.size())).activate(mob);
            }
        }
    }

    public void tickPhase() {
        if(!phases.isEmpty()) {
            Phase currentPhase = phases.getFirst();
            double currentHealth = mob.getHealth() / maxHealth;
            for(Phase phase : phases) {
                if(currentPhase.getMaxHealthRange()>phase.getMaxHealthRange()&&phase.getMaxHealthRange()>=currentHealth) {
                    currentPhase = phase;
                }
            }
            if(activePhase!=currentPhase) {
                activePhase = currentPhase;
            }
        }
    }

    public void tickBossBar() {
        ArrayList<Player> players = new ArrayList<Player>(mob.getLocation().getNearbyPlayers(100));
        if(!players.isEmpty()) {
            // Add nearby players so they can see the boss bar
            for(Player player : players) {
                if(!bossBar.getPlayers().contains(player)) {
                    bossBar.addPlayer(player);
                }
            }
        }
        // Remove players who are no longer nearby
        if(!bossBar.getPlayers().isEmpty()) {
            for (Player player : bossBar.getPlayers()) {
                if (!players.contains(player)) {
                    bossBar.removePlayer(player);
                    BossPlugin.getPlugin().getLogger().info(player.getName()+" removed from bossbar");
                }
            }
            double percentage = mob.getHealth() / mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            bossBar.setProgress(percentage);
        }
    }

    public void removeBossBar() {
        bossBar.removeAll();
    }

    public boolean isBossDead() {
        return mob.isDead();
    }

    public void setName(String name) {
        this.name = name;
    }
}
