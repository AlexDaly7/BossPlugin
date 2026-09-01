package Alex.bossPlugin.menus;

import Alex.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.List;

public class TransitionMenu extends Menu {
    enum inputEnum {
        TRANSITIONTIME,
        PARTICLE
    }


    public TransitionMenu(Player player, MenuSession session) {
        super(player, session);
        menu = Bukkit.createInventory(player, 36, "Transition Menu");
    }

    @Override
    public void handleClick(int slot) {
        switch(slot) {
            case 27 -> session.openLastMenu();

            case 0 -> {
                preTextInput();
                player.sendMessage("Please enter the transition time for this phase");
                currentInput = inputEnum.TRANSITIONTIME;
            }
            case 1 -> {
                preTextInput();
                player.sendMessage("Please enter the transition particle for this phase");
                player.sendMessage("https://minecraft.fandom.com/wiki/Particles#Types_of_particles");
                currentInput = inputEnum.PARTICLE;
            }
        }
    }

    @Override
    public void handleTextInput(String input) {
        switch((inputEnum) currentInput) {
            case TRANSITIONTIME -> {
                int time;
                try {
                    time = Integer.parseInt(input);
                    if(time<1) {
                        player.sendMessage("Please ensure the time is over 0");
                    } else {
                        session.getPhase().setTransitionTime(time);
                        openSelf();
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage("That is not a valid number");
                }
            }
            case PARTICLE -> {
                Particle particle;
                try {
                    particle = Particle.valueOf(input);
                    session.getPhase().setParticle(particle);
                    openSelf();
                } catch (IllegalArgumentException e) {
                    player.sendMessage("That is a not a valid particle");
                }
            }
        }
    }

    @Override
    public void openSelf() {

        menu.setItem(0,
            MenuUtil.createButton(
                Material.CLOCK,
                Component.text("Click to set transition time of this phase"),
                List.of(
                    Component.text("When switching phases, bosses are invulnerable"),
                    Component.text("for the amount of time set here."),
                    Component.text("Current time is "+session.getPhase().getTransitionTime()+".")
                )
            )
        );

        menu.setItem(1,
            MenuUtil.createButton(
                Material.STRUCTURE_BLOCK,
                Component.text("Click to set the transition particle"),
                List.of(Component.text("While the boss is invulnerable, this particle will appear"))
            )
        );

        menu.setItem(27,
            MenuUtil.createButton(
                Material.CRYING_OBSIDIAN,
                Component.text("Go back to previous menu"),
                List.of(Component.text("Click to go back to the previous menu"))
            )
        );

        player.openInventory(menu);
    }
}
