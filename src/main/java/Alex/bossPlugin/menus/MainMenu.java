package Alex.bossPlugin.menus;

import Alex.bossPlugin.bosses.CustomBoss;
import Alex.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class MainMenu extends Menu {

    public MainMenu(Player player, MenuSession session) {
        super(player, session);
        menu = Bukkit.createInventory(player, 45, "Main Menu");
        menu.setItem(0,
                MenuUtil.createButton(
                    Material.SPAWNER,
                    Component.text("Bosses"),
                    List.of(Component.text("Click to see all active bosses"))
                )
        );
        menu.setItem(9,
                MenuUtil.createButton(
                    Material.NETHER_STAR,
                    Component.text("Create boss"),
                    List.of(Component.text("Click to create boss"))
                )
        );
        openSelf();
    }

    @Override
    public void handleClick(int slot) {
        switch(slot) {
            case 0 -> {
                session.openMenu(new BossListMenu(player, session));
            }
            case 9 -> {
                session.setBoss(new CustomBoss(player.getWorld(), player.getLocation()));
                BossCreationMenu bossCreationMenu = new BossCreationMenu(player, session);

                session.openMenu(bossCreationMenu);
            }
        }
    }
}
