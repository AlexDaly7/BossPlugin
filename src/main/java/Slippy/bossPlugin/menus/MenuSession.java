package Slippy.bossPlugin.menus;

import Slippy.bossPlugin.bosses.CustomBoss;
import Slippy.bossPlugin.bosses.Phase;
import org.bukkit.entity.Player;

import java.util.*;

public class MenuSession {
    public static Map<UUID, MenuSession> sessions = new HashMap<UUID, MenuSession>();
    private Menu currentMenu;
    private CustomBoss boss;
    private Phase phase;
    private List<Menu> menuHistory = new ArrayList<>();

    public MenuSession(Player player) {
        Menu mainMenu = new MainMenu(player, this);
        currentMenu = mainMenu;
        menuHistory.add(mainMenu);
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

    public void openMenu(Menu nextMenu) {
        currentMenu.close();
        menuHistory.add(nextMenu);
        currentMenu = nextMenu;
        currentMenu.openSelf();
    }

    public void openLastMenu() {
        currentMenu.close();
        menuHistory.removeLast();
        currentMenu = menuHistory.getLast();
        currentMenu.openSelf();
    }

    public void openCurrentMenu() {
        currentMenu.openSelf();
    }

    public void setBoss(CustomBoss boss) {
        this.boss = boss;
    }

    public CustomBoss getBoss() {
        return boss;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Phase getPhase() {
        return phase;
    }
}
