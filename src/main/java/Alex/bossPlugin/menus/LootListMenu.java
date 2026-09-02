package Alex.bossPlugin.menus;

import Alex.bossPlugin.BossPlugin;
import Alex.bossPlugin.bosses.CustomBoss;
import Alex.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LootListMenu extends MultiPageMenu {
    List<Map<String, Object>> lootTable;
    enum inputEnum {
        CREATELOOT
    }

    public LootListMenu(Player player, MenuSession session) {
        super(player, session);
    }

    @Override
    public void handleClick(int slot) {

        if(slot<45) {
            if (slot+(currentPage*45)< lootTable.size()) {
                session.openMenu(new LootMenu(player, session, lootTable.get((slot+(currentPage*45)))));
            }
        }
        switch(slot) {
            case 45 -> {
                session.openLastMenu();
            }
            case 49 -> {
                preTextInput();
                player.sendMessage("Please enter the item you wish to add");
                currentInput = inputEnum.CREATELOOT;
            }
        }
    }

    @Override
    public void handleTextInput(String input) {
        switch((inputEnum) currentInput) {
            case CREATELOOT -> {
                Material material;
                try {
                    material = Material.valueOf(input.toUpperCase());
                    Map<String, Object> map = new HashMap<>();
                    map.put("item", material);
                    session.openMenu(new LootMenu(player, session, map));
                } catch (IllegalArgumentException e) {
                    player.sendMessage("That is not a valid item");
                }
            }
        }
    }

    @Override
    public void openSelf() {
        pages.clear();
        items.clear();

        lootTable = session.getBoss().getLootList();
        for(int i=0;i<lootTable.size();i++) {
            if(lootTable.get(i).containsKey("item")) {
                items.add(
                    MenuUtil.createButton(
                        (Material) lootTable.get(i).get("item"),
                        Component.text(((Material) lootTable.get(i).get("item")).name()),
                        List.of(Component.text("Click to open this items menu"))
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
            inventory.setItem(49,
                MenuUtil.createButton(
                    Material.NETHER_STAR,
                    Component.text("Add new loot item"),
                    List.of(Component.text("Click to add a new item to the loot table"))
                )
            );
        }

        player.openInventory(pages.getFirst());
    }
}
