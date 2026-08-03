package Slippy.bossPlugin.menus;

import Slippy.bossPlugin.abilities.Ability;
import Slippy.bossPlugin.bosses.Phase;
import Slippy.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

public class AbilityListMenu extends MultiPageMenu {
    private List<Ability> abilities;

    public AbilityListMenu(Player player, MenuSession session) {
        super(player, session);
        menu = Bukkit.createInventory(player, 54);
    }

    @Override
    public void handleClick(int slot) {
        if(pageChangeClick(slot)) return;
        if(slot<45) {
            
        } else if(slot==45) {
            session.openLastMenu();
        }
    }

    @Override
    public void openSelf() {
        if(!abilities.isEmpty()) {
            abilities.forEach(ability -> {
                items.add(
                        MenuUtil.createButton(
                                ability.getItem(),
                                Component.text(ability.getName()),
                                List.of(Component.text(ability.getLore()))
                        )
                );
            });
        }
        player.sendMessage(""+pages.size());

        fillPages();

        pages.forEach(inventory -> {
            inventory.setItem(48,
                    MenuUtil.createButton(
                            Material.NETHER_STAR,
                            Component.text("Add ability"),
                            List.of(Component.text("Click to add an ability to this ability list"))
                    )
            );
            inventory.setItem(45,
                    MenuUtil.createButton(
                            Material.CRYING_OBSIDIAN,
                            Component.text("Go back to previous menu"),
                            List.of(Component.text("Click to go back to the previous menu"))
                    )
            );
        });

        player.openInventory(pages.getFirst());
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }
}
