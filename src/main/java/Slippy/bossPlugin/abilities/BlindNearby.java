package Slippy.bossPlugin.abilities;

import Slippy.bossPlugin.util.TaskUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class BlindNearby extends Ability {

    public BlindNearby(int range) {
        super(range);
    }

    @Override
    public void activate(Mob mob) {
        Location loc = mob.getLocation();
        World world = mob.getWorld();
        ArrayList<Player> players = new ArrayList<Player>(loc.getNearbyPlayers(range));
        if(players.getFirst()!=null) {
            // Effects around mob who activated ability
            TaskUtil.runTimedTask(() -> {
                world.spawnParticle(
                        Particle.EFFECT,
                        loc,
                        20,
                        1, 1, 1
                        );
            }, 0, 1, 20);
            // Apply effect to players in range
            for(Player player : players) {
                player.addPotionEffect(
                        PotionEffectType.BLINDNESS.createEffect(20, 5)
                );
            }
        }
    }
}
