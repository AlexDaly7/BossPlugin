package Slippy.bossPlugin.listeners;

import Slippy.bossPlugin.menus.MenuSession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.UUID;

public class MenuCloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(event.getReason()==InventoryCloseEvent.Reason.PLUGIN) return;
        UUID uuid = event.getPlayer().getUniqueId();
        MenuSession session = MenuSession.getSession(uuid);
        if(session!=null) {
            MenuSession.removeSession(uuid);
        }
    }
}
