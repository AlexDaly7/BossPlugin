package Alex.bossPlugin.menus;

import Alex.bossPlugin.bosses.BaseBoss;
import Alex.bossPlugin.bosses.BossManager;
import Alex.bossPlugin.bosses.CustomBoss;
import Alex.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class BossListMenu extends MultiPageMenu {
    List<BaseBoss> bosses;

    public BossListMenu(Player player, MenuSession session) {
        super(player, session);
        // TOFIX: cannot recreate issue, but boss mob was deleted after fiddling around in this menu
    }

    @Override
    public void handleClick(int slot) {
        if(pageChangeClick(slot)) return;
        if(slot<45) {
            if (slot+(currentPage*45)<bosses.size()) {
                session.setBoss((CustomBoss) bosses.get(slot + (currentPage * 45)));
                session.openMenu(new BossCreationMenu(player, session));
            }
        }
        switch(slot) {
            case 45 -> {
                session.openLastMenu();
            }
        }
    }

    @Override
    public void openSelf() {
        items.clear();
        bosses = BossManager.getBosses();
        for(BaseBoss boss : bosses) {
            items.add(
                    MenuUtil.createButton(
                            Material.SPAWNER,
                            Component.text(boss.getName()),
                            List.of(Component.text("Placeholder"))
                    )
            );
        }

        fillPages();

        for(Inventory inventory : pages) {
            inventory.setItem(45,
                    MenuUtil.createButton(
                            Material.CRYING_OBSIDIAN,
                            Component.text("Go back to previous menu"),
                            List.of(Component.text("Click to go back to the previous menu"))
                    )
            );
        }

        player.openInventory(pages.getFirst());
    }
}
