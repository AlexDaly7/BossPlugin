package Alex.bossPlugin.menus;

import Alex.bossPlugin.BossPlugin;
import Alex.bossPlugin.bosses.CustomBoss;
import Alex.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class LootListMenu extends MultiPageMenu {
    List<Map<String, Object>> lootTable;

    public LootListMenu(Player player, MenuSession session) {
        super(player, session);
    }

    @Override
    public void handleClick(int slot) {

        if(slot<45) {
            if (slot+(currentPage*45)< lootTable.size()) {

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
        lootTable = session.getBoss().getLootList();
        for(int i=0;i<lootTable.size();i++) {
            Material material;
            if(lootTable.get(i).containsKey("item")) {
                items.add(
                    MenuUtil.createButton(
                        (Material) lootTable.get(i).get("item"),
                        Component.text(((Material) lootTable.get(i).get("item")).name()),
                        List.of(Component.text(""))
                    )
                );
            }
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
