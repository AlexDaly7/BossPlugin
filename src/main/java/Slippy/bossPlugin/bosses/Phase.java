package Slippy.bossPlugin.bosses;

import Slippy.bossPlugin.abilities.Ability;

import java.util.ArrayList;

public class Phase {

    private double maxHealthRange;
    private ArrayList<Ability> baseAbilities = new ArrayList<Ability>();
    private ArrayList<Ability> specialAbilities = new ArrayList<Ability>();
    private int maxBaseCooldown;
    private int maxSpecialCooldown;

    public Phase(double maxHealthRange, ArrayList<Ability> baseAbilities, ArrayList<Ability> specialAbilities, int maxBaseCooldown, int maxSpecialCooldown) {
        this.maxHealthRange = maxHealthRange;
        this.baseAbilities = baseAbilities;
        this.specialAbilities = specialAbilities;
        this.maxBaseCooldown = maxBaseCooldown;
        this.maxSpecialCooldown = maxSpecialCooldown;
    }

    public double getMaxHealthRange() {
        return maxHealthRange;
    }

    public ArrayList<Ability> getBaseAbilities() {
        return baseAbilities;
    }

    public ArrayList<Ability> getSpecialAbilities() {
        return specialAbilities;
    }

    public int getMaxSpecialCooldown() {
        return maxSpecialCooldown;
    }

    public int getMaxBaseCooldown() {
        return maxBaseCooldown;
    }
}
