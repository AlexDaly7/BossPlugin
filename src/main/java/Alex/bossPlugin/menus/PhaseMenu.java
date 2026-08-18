package Alex.bossPlugin.menus;

import Alex.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class PhaseMenu extends Menu {
    private enum inputEnum {
        HEALTH
    }

    public PhaseMenu(Player player, MenuSession session) {
        super(player, session);
        menu = Bukkit.createInventory(player, 36, "Phase Menu");
    }

    @Override
    public void handleClick(int slot) {
        switch(slot) {
            case 27 -> {
                session.openLastMenu();
            }
            case 0 -> {
                preTextInput();
                player.sendMessage("Enter the percentage of health for this phase to activate at. (100%=1.0/50%=0.5)");
                currentInput = inputEnum.HEALTH;
            }
            case 1 -> {
                AbilityListMenu abilityMenu = new AbilityListMenu(player, session);
                abilityMenu.setSpecial();
                session.openMenu(abilityMenu);
            }
            case 2 -> {
                AbilityListMenu abilityMenu = new AbilityListMenu(player, session);
                session.openMenu(abilityMenu);
            }
        }
    }

    @Override
    public void handleTextInput(String input) {
        switch((inputEnum) currentInput) {
            case HEALTH -> {
                double health;
                try {
                    health = Double.parseDouble(input);
                    if(health>1) {
                        player.sendMessage("Enter the percentage of health for this phase to activate at. (100%=1.0/50%=0.5)");
                    } else {
                        session.getPhase().setMaxHealthRange(health);
                        openSelf();
                    }
                } catch(NumberFormatException e) {
                    player.sendMessage("That is not a valid number.");
                }



            }
        }
    }

    @Override
    public void openSelf() {
        menu.setItem(27,
            MenuUtil.createButton(
                Material.CRYING_OBSIDIAN,
                Component.text("Go back to previous menu"),
                List.of(Component.text("Click to go back to the previous menu"))
            )
        );

        menu.setItem(0,
            MenuUtil.createButton(
                Material.REDSTONE_BLOCK,
                Component.text("Set health to transition at"),
                List.of(
                        Component.text("Sets the percentage of health that the"),
                        Component.text("boss will transition to this phase at."),
                        Component.text("The current value is "+session.getPhase().getMaxHealthRange())
                )
            )
        );
        menu.setItem(1,
            MenuUtil.createButton(
                Material.NETHER_STAR,
                Component.text("Open special abilities menu"),
                List.of(Component.text("Click to open special abilities menu"))
            )
        );
        menu.setItem(2,
                MenuUtil.createButton(
                        Material.NETHER_STAR,
                        Component.text("Open base abilities menu"),
                        List.of(Component.text("Click to open base abilities menu"))
                )
        );

        player.openInventory(menu);
    }
}
