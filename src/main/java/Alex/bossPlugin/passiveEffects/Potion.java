package Alex.bossPlugin.passiveEffects;

import Alex.bossPlugin.BossPlugin;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.Map;

public class Potion extends PassiveEffect {
    String potion;

    public Potion(Map<String, Object> data) {
        super(data);
        potion = data.containsKey("potion") ? (String) data.get("potion") : "SLOW_FALLING";
    }

    @Override
    public void activate(Mob mob) {
        Collection<Player> players = mob.getLocation().getNearbyPlayers(range);

        PotionEffect potionEffect;
        if(!players.isEmpty()) {
            try {
                potionEffect = PotionEffectType.getByName(potion).createEffect(80, amplifier);
            } catch (IllegalArgumentException e) {
                BossPlugin.getPlugin().getLogger().warning("Potion " + (String) potion + " is not a valid potion.");
                return;
            }

            for(Player player : players) {
                player.addPotionEffect(potionEffect);
            }
        }
    }

    @Override
    public Map<String, Object> getData() {
        return Map.of("potion", potion);
    }
}
