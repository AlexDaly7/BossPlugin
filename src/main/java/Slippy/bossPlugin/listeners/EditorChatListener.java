package Slippy.bossPlugin.listeners;

import Slippy.bossPlugin.menus.MenuSession;
import io.papermc.paper.event.player.ChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import java.util.UUID;

public class EditorChatListener implements Listener {

    @EventHandler
    public void onChat(ChatEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        MenuSession session = MenuSession.getSession(uuid);

        if(session!=null) {
            String input = PlainTextComponentSerializer.plainText().serialize(event.message());
            if(input.equalsIgnoreCase("exit")) {
                session.openCurrentMenu();
                return;
            }
            session.getCurrentMenu().handleTextInput(input);

        }
    }
}
