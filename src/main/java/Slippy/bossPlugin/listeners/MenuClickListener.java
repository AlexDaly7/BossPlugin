package Slippy.bossPlugin.listeners;

import Slippy.bossPlugin.menus.Menu;
import Slippy.bossPlugin.menus.MenuSession;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuClickListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        MenuSession session = MenuSession.getSession(player.getUniqueId());
        if(session!=null) {
            session.getCurrentMenu().handleClick(event.getSlot());
        }
    }
}
