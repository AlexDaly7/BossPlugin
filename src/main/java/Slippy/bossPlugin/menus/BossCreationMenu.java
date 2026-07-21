package Slippy.bossPlugin.menus;

import Slippy.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class BossCreationMenu extends Menu {
    public BossCreationMenu(Player player, MenuSession session) {
        super(player, session);
        menu = Bukkit.createInventory(player, 45, "Boss Creation");
        menu.setItem(0,
                MenuUtil.createButton(
                        Material.SPAWNER,
                        Component.text("Set boss name"),
                        List.of(Component.text("Click to set boss name"))
                )
        );
    }
}
