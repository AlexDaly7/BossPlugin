package Alex.bossPlugin.abilities;

import Alex.bossPlugin.util.TaskUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;

public class Knockback extends Ability {

    public Knockback(Map<String, Object> data) {
        super(data);
        name = "Knockback";
        lore = "Throws all players back from the boss";
        displayItem = Material.ENCHANTED_BOOK;
    }

    @Override
    public void activate(Mob mob) {
        int range = data.containsKey("range") ? (int) data.get("range") : 20;

        List<Player> players = mob.getLocation().getNearbyPlayers(range).stream().toList();

        if(players!=null||!players.isEmpty()) {
            // Spawn particles to warn player Knockback Is Imminent!
            TaskUtil.runTimedTaskWithEnd(
                () -> {
                    mob.getWorld().spawnParticle(
                        Particle.EXPLOSION,
                        mob.getLocation(),
                        5
                    );
                }, 0, 5, 4,
                    // Spawn more particles to telegraph attack is happening
                    () -> {
                        mob.getWorld().spawnParticle(
                            Particle.CLOUD,
                            mob.getLocation(),
                            50
                        );
                        // Throw the player back using a vector from the bosses location
                        for(Player player: players) {
                            Location mobLoc = mob.getLocation().clone();
                            mobLoc.setY(player.getY());
                            player.setVelocity(
                                player.getLocation().toVector()
                                        .subtract(
                                            mobLoc.add(new Vector(0, -1, 0))
                                                .toVector())
                                        .normalize()
                                        .multiply(2.8)
                            );
                        }
                    }
            );


        }


    }
}
