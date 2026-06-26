package Slippy.bossPlugin.listeners;

import Slippy.bossPlugin.bosses.BaseBoss;
import Slippy.bossPlugin.bosses.BossManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class BossDeathListener implements Listener {

    @EventHandler
    public void onBossDeath(EntityDeathEvent event) {
        if(!event.getEntity().getScoreboardTags().contains("boss")) return;
        for(BaseBoss boss : BossManager.getBosses()) {
            List<Map<String, Object>> lootList = boss.getLootList();
            for(Map<String, Object> loot : lootList) {
                event.getDrops().add(new ItemStack((Material) loot.get("item"),(int) loot.get("amount")));
            }
        }
    }
}
