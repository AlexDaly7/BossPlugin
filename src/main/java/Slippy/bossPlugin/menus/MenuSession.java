package Slippy.bossPlugin.menus;

import Slippy.bossPlugin.bosses.BaseBoss;
import Slippy.bossPlugin.bosses.CustomBoss;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MenuSession {
    public static Map<UUID, MenuSession> sessions = new HashMap<UUID, MenuSession>();
    private Menu currentMenu;
    private Menu lastMenu;
    private CustomBoss boss;

    public MenuSession(Player player) {
        currentMenu = new MainMenu(player, this);
    }

    public static void addSession(Player player) {
        sessions.put(player.getUniqueId(), new MenuSession(player));
    }

    public static void removeSession(UUID uuid) {
        sessions.remove(uuid);
    }

    public static MenuSession getSession(UUID uuid) {
        return sessions.get(uuid);
    }

    public static Map<UUID, MenuSession> getSessions() {
        return sessions;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu menu) {
        currentMenu = menu;
    }

    public void openMenu(Menu nextMenu) {
        currentMenu.close();
        lastMenu = currentMenu;
        currentMenu = nextMenu;
        currentMenu.openSelf();
    }

    public void setBoss(CustomBoss boss) {
        this.boss = boss;
    }

    public CustomBoss getBoss() {
        return boss;
    }
}
