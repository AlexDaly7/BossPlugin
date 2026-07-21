package Slippy.bossPlugin.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class Heal extends Ability {
    // TODO: Add option for percentage heal of health, either within this ability or in another
    public Heal(Map<String, Object> data) {
        super(data);
        name = "Heal";
        lore = "Heals the boss.";
        displayItem = new ItemStack(Material.POTION);
    }

    @Override
    public void activate(Mob mob) {
        int amount = data.containsKey("amount") ? (int) data.get("amount") : 10;

        mob.heal(amount);
    }
}
