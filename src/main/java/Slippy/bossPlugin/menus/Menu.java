package Slippy.bossPlugin.menus;

import Slippy.bossPlugin.bosses.BaseBoss;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Menu {
    protected Player player;
    protected MenuSession session;
    protected Inventory menu;
    protected Enum<?> currentInput;
    protected String menuName = "Menu";

    public Menu(Player player, MenuSession session) {
        this.player = player;
        this.session = session;
    }

    public void handleClick(int slot) {}

    public void handleTextInput(String input) {}

    public void openSelf() {
        player.openInventory(menu);
    }

    public void close() {
        menu.close();
        player.playSound(
                Sound.sound(
                        Key.key("minecract:ui.button.click"),
                        Sound.Source.MASTER,
                        1.0f,
                        1.0f
                )
        );
    }
}
