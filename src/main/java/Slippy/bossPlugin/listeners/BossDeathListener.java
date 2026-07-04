package Slippy.bossPlugin.listeners;

import Slippy.bossPlugin.BossPlugin;
import Slippy.bossPlugin.bosses.BaseBoss;
import Slippy.bossPlugin.bosses.BossManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class BossDeathListener implements Listener {

    @EventHandler
    public void onBossDeath(EntityDeathEvent event) {
        // TODO: Put out fire when boss dies to not destroy drops
        if(!event.getEntity().getScoreboardTags().contains("boss")) return;
        for(BaseBoss boss : BossManager.getBosses()) {
            List<Map<String, Object>> lootList = boss.getLootList();
            Location loc = boss.getLocation();
            double radius = 3;
            loc.add(-((radius/2)-1),0,-((radius/2)-1));

            for(int i=0;i<=radius;i++) {
                loc.add(0,0,-radius);
                for(int j=0;j<=radius;j++) {
                    BossPlugin.getPlugin().getLogger().info(loc.getX()+", "+loc.getY()+", "+loc.getZ());
                    if(loc.getBlock().getType()==Material.FIRE) {
                        BossPlugin.getPlugin().getLogger().info("Block Replaced");
                        loc.getBlock().setType(Material.AIR);
                    }
                    loc.add(0,0,1);
                }
                loc.add(1,0,0);
            }
            for(Map<String, Object> loot : lootList) {
                event.getDrops().add(new ItemStack((Material) loot.get("item"),(int) loot.get("amount")));
            }

        }
    }
}
