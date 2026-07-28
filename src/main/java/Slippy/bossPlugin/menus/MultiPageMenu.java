package Slippy.bossPlugin.menus;

import Slippy.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MultiPageMenu extends Menu {
    protected List<Inventory> pages = new ArrayList<>();
    protected List<ItemStack> items = new ArrayList<>();
    protected int currentPage = 0;

    public MultiPageMenu(Player player, MenuSession session) {
        super(player, session);
    }

    @Override
    public void openSelf() {
        player.openInventory(pages.getFirst());
    }

    public void fillPages() {
        int pageCount = (items.size() / 45);
        for(int i=0;i<=pageCount;i++) {
            Inventory inventory = Bukkit.createInventory(player, 54, menuName);
            inventory.setItem(47,
                    MenuUtil.createButton(
                            Material.COMMAND_BLOCK,
                            Component.text("Go back to previous page"),
                            List.of(Component.text("Click to go back to previous page"))
                    )
            );
            inventory.setItem(51,
                    MenuUtil.createButton(
                            Material.COMMAND_BLOCK,
                            Component.text("Go forward to next page"),
                            List.of(Component.text("Click to go forward to next page"))
                    )
            );
            pages.add(inventory);
        }
        for(int i=0;i<items.size();i++) {
            int pageNumber = i / 45;
            pages.get(pageNumber).setItem(i-(pageNumber*45), items.get(i));
        }
    }

    public boolean pageChangeClick(int slot) {
        boolean activated = true;
        switch(slot) {
            case 47 -> {
                if(currentPage>0&&!pages.isEmpty()) {
                    pages.get(currentPage).close();
                    currentPage-=1;
                    player.openInventory(pages.get(currentPage));
                }
            }
            case 51 -> {
                if(currentPage<pages.size()-1&&!pages.isEmpty()) {
                    pages.get(currentPage).close();
                    currentPage+=1;
                    player.openInventory(pages.get(currentPage));
                }
            }
            default -> activated = false;
        }
        return activated;
    }

    @Override
    public void close() {
        pages.get(currentPage).close();
    }
}
