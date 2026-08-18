package Alex.bossPlugin.menus;

import Alex.bossPlugin.abilities.Ability;
import Alex.bossPlugin.abilities.AbilityType;
import Alex.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddAbilityMenu extends MultiPageMenu {
    private List<Ability> abilities = new ArrayList<Ability>();
    // Tells the menu to submit ability to special or base abilities list
    boolean isSpecialAbility = false;

    public AddAbilityMenu(Player player, MenuSession session) {
        super(player, session);

        for(AbilityType ability : AbilityType.values()) {
            abilities.add(
                    ability.create(Map.of())
            );
        }
        for(Ability ability : abilities) {
            items.add(
                MenuUtil.createButton(
                    ability.getItem(),
                    Component.text(ability.getName()),
                    List.of(Component.text(ability.getLore()))
                )
            );
        fillPages();

            pages.forEach(inventory -> {
                inventory.setItem(45,
                        MenuUtil.createButton(
                                Material.CRYING_OBSIDIAN,
                                Component.text("Go back to previous menu"),
                                List.of(Component.text("Click to go back to the previous menu"))
                        )
                );
            });
        }
    }

    @Override
    public void handleClick(int slot) {
        if(pageChangeClick(slot)) return;
        if(slot<45) {
            if(slot+(currentPage*45)>abilities.size()-1) {
                return;
            }
            if(isSpecialAbility) {
                session.getPhase().addSpecialAbility(abilities.get(slot+(currentPage*45)));
            } else {
                session.getPhase().addBaseAbility(abilities.get(slot+(currentPage*45)));
            }
            session.openLastMenu();
        } else if(slot==48) {
            session.openMenu(new AddAbilityMenu(player, session));
        } else if(slot==45) {
            session.openLastMenu();
        }
    }

    public void setSpecialAbility() {
        isSpecialAbility = true;
    }
}
