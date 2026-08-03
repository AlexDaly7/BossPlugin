package Slippy.bossPlugin.abilities;

import Slippy.bossPlugin.util.TaskUtil;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Map;

public class Meteor extends Ability {

    public Meteor(Map<String, Object> data) {
        super(data);
        name = "Meteor";
        lore = "Sends meteors from the sky at each player within range.";
        displayItem = Material.MAGMA_BLOCK;
    }

    @Override
    public void activate(Mob mob) {
        Location loc = mob.getLocation();
        int range = data.containsKey("range") ? (int) data.get("range") : 50;

        if(!loc.getNearbyPlayers(range).isEmpty()) {
            for(Player player : loc.getNearbyPlayers(range)) {
                Location playerLoc = player.getLocation();
                Entity fireball = mob.getWorld().spawnEntity(playerLoc.clone().add(0,100,0), EntityType.FIREBALL);
                fireball.setVelocity(new Vector(0,-0.1,0));
                TaskUtil.runTimedTaskWithEnd(() -> {
                    mob.getWorld().spawnParticle(Particle.FALLING_LAVA,
                            playerLoc.clone().add(0,20,0),
                            20,
                            1, 20, 1
                    );
                }, 0, 5, 12, () -> {
                    fireball.setVelocity(new Vector(0,-1,0));
                    fireball.setVisualFire(true);
                });
                }
        }



    }
}
