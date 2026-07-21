package Slippy.bossPlugin.listeners;

import Slippy.bossPlugin.menus.MenuSession;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.UUID;

public class EditorChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        MenuSession session = MenuSession.getSession(uuid);

        if(session!=null) {

        }

    }
}
