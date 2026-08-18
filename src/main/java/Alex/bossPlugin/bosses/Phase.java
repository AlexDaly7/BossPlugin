package Alex.bossPlugin.bosses;

import Alex.bossPlugin.abilities.Ability;
import Alex.bossPlugin.passiveEffects.PassiveEffect;
import org.bukkit.Particle;
import org.bukkit.entity.Mob;

import java.util.ArrayList;
import java.util.List;

public class Phase {

    private double maxHealthRange;
    private List<Ability> baseAbilities = new ArrayList<Ability>();
    private List<Ability> specialAbilities = new ArrayList<Ability>();
    private int maxBaseCooldown;
    private int maxSpecialCooldown;
    private Particle particle;
    private double transitionTime = 2;
    private List<PassiveEffect> passiveEffects = new ArrayList<PassiveEffect>();
    private Mob mob;

    public Phase(double maxHealthRange, ArrayList<Ability> baseAbilities, ArrayList<Ability> specialAbilities, int maxBaseCooldown, int maxSpecialCooldown) {
        this.maxHealthRange = maxHealthRange;
        this.baseAbilities = baseAbilities;
        this.specialAbilities = specialAbilities;
        this.maxBaseCooldown = maxBaseCooldown;
        this.maxSpecialCooldown = maxSpecialCooldown;
    }

    public Phase(double maxHealthRange, int maxBaseCooldown, int maxSpecialCooldown) {
        this.maxHealthRange = maxHealthRange;
        this.maxBaseCooldown = maxBaseCooldown;
        this.maxSpecialCooldown = maxSpecialCooldown;
    }

    public Phase() {}

    public double getMaxHealthRange() {
        return maxHealthRange;
    }

    public void setMaxHealthRange(double maxHealth) {
        maxHealthRange = maxHealth;
    }

    public List<Ability> getBaseAbilities() {
        return baseAbilities;
    }

    public List<Ability> getSpecialAbilities() {
        return specialAbilities;
    }

    public void setBaseAbilities(List<Ability> baseAbilities) {
        this.baseAbilities = baseAbilities;
    }

    public void setSpecialAbilities(List<Ability> specialAbilities) {
        this.specialAbilities = specialAbilities;
    }

    public Ability getRanSpecialAbility() {
        return specialAbilities.get((int)(Math.random()*specialAbilities.size()));
    }

    public Ability getRanBaseAbility() {
        return baseAbilities.get((int)(Math.random()*baseAbilities.size()));
    }

    public int getMaxSpecialCooldown() {
        return maxSpecialCooldown;
    }

    public int getMaxBaseCooldown() {
        return maxBaseCooldown;
    }

    public void setParticle(Particle particle) {
        this.particle = particle;
    }

    public Particle getParticle() {
        return particle;
    }

    public void setTransitionTime(double transTime) {
        transitionTime = transTime;
    }

    public double getTransitionTime() {
        return transitionTime;
    }

    public void addEffect(PassiveEffect passiveEffect) {
        passiveEffects.add(passiveEffect);
    }

    public void activateEffects(Mob mob) {
        for(PassiveEffect effect : passiveEffects) {
            effect.activate(mob);
        }
    }

    public void addBaseAbility(Ability ability) {
        baseAbilities.add(ability);
    }

    public void addSpecialAbility(Ability ability) {
        specialAbilities.add(ability);
    }

    public void setMob(Mob mob) {
        this.mob = mob;
    }
}
