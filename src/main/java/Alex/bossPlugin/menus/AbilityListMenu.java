package Alex.bossPlugin.menus;

import Alex.bossPlugin.abilities.Ability;
import Alex.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class AbilityListMenu extends MultiPageMenu {
    private List<Ability> abilities;
    boolean isSpecial = false;

    public AbilityListMenu(Player player, MenuSession session) {
        super(player, session);
        menu = Bukkit.createInventory(player, 54);
    }

    @Override
    public void handleClick(int slot) {
        if(pageChangeClick(slot)) return;
        if(slot<45) {
            if(slot+(currentPage*45)>session.getBoss().getPhases().size()-1) {
                return;
            }
            //AbilityMenu abilityMenu = new AbilityMenu(player, session);
            //abilityMenu.setAbility(session.getBoss().getPhase(slot+(currentPage*45)));
            //session.openMenu(abilityMenu);
        } else if(slot==48) {
            AddAbilityMenu addAbilityMenu = new AddAbilityMenu(player, session);
            if(isSpecial) {
                addAbilityMenu.setSpecialAbility();
            }
            session.openMenu(addAbilityMenu);
        } else if(slot==45) {
            session.openLastMenu();
        }
    }

    @Override
    public void openSelf() {
        if(isSpecial) {
            abilities = session.getPhase().getSpecialAbilities();
        } else {
            abilities = session.getPhase().getBaseAbilities();
        }

        if(!abilities.isEmpty()) {
            items.clear();
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

    public void setSpecial() {
        isSpecial = true;
    }
}
