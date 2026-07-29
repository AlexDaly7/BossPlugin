package Slippy.bossPlugin.menus;

import Slippy.bossPlugin.bosses.Phase;
import Slippy.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class PhaseMenu extends Menu {
    private enum inputEnum {
        HEALTH
    }
    private Phase phase;

    public PhaseMenu(Player player, MenuSession session) {
        super(player, session);
        menu = Bukkit.createInventory(player, 36, "Phase Menu");

        menu.setItem(0,
            MenuUtil.createButton(
                Material.REDSTONE_BLOCK,
                Component.text("Set health to transition at"),
                List.of(
                    Component.text("Sets the percentage of health that the"),
                    Component.text("boss will transition to this phase at.")
                )
            )
        );
    }

    @Override
    public void handleClick(int slot) {
        switch(slot) {
            case 0 -> {
                menu.close();
                player.sendMessage("Enter the percentage of health for this phase to activate at. (100%=1.0/50%=0.5)");
                currentInput = inputEnum.HEALTH;
            }
        }
    }

    @Override
    public void handleTextInput(String input) {
        switch((inputEnum) currentInput) {
            case HEALTH -> {
                // TODO: Fix parsing issue and streamline text input through separate object
                player.sendMessage(""+input);
                double health = Double.parseDouble(input);

                if(health>1) {
                    player.sendMessage("Enter the percentage of health for this phase to activate at. (100%=1.0/50%=0.5)");
                } else {
                    openSelf();
                }
            }
        }
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }
}
