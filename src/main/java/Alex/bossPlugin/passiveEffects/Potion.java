package Alex.bossPlugin.passiveEffects;

import Alex.bossPlugin.BossPlugin;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.Map;

public class Potion extends PassiveEffect {

    public Potion(Map<String, Object> data) {
        super(data);
    }

    @Override
    public void activate(Mob mob) {
        int range = data.containsKey("range") ? (int) data.get("range") : 10;
        int amplifier = data.containsKey("amplifier") ? (int) data.get("amplifier") : 2;
        Collection<Player> players = mob.getLocation().getNearbyPlayers(range);

        PotionEffect potionEffect;
        if(!players.isEmpty()) {
            if(data.containsKey("potion")) {
                try {
                    potionEffect = PotionEffectType.getByName((String) data.get("potion")).createEffect(80, amplifier);
                } catch (IllegalArgumentException e) {
                    BossPlugin.getPlugin().getLogger().warning("Potion " + (String) data.get("potion") + " is not a valid potion.");
                    return;
                }
            } else {
                    BossPlugin.getPlugin().getLogger().warning("Missing potion field, default potion used");
                    potionEffect = PotionEffectType.SLOWNESS.createEffect(80, amplifier);
            }
            for(Player player : players) {
                player.addPotionEffect(potionEffect);
            }
        }
    }
}
