package Slippy.bossPlugin.menus;

import Slippy.bossPlugin.bosses.Phase;
import Slippy.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class PhaseListMenu extends MultiPageMenu {
    public PhaseListMenu(Player player, MenuSession session) {
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
            PhaseMenu phaseMenu = new PhaseMenu(player, session);
            session.setPhase(session.getBoss().getPhase(slot+(currentPage*45)));
            session.openMenu(phaseMenu);
        } else if(slot==48) {
            PhaseMenu phaseMenu = new PhaseMenu(player, session);
            Phase phase = new Phase();
            session.setPhase(phase);
            session.getBoss().addPhase(phase);
            session.openMenu(phaseMenu);
        } else if(slot==45) {
            session.openLastMenu();
        }
    }

    @Override
    public void openSelf() {
        List<Phase> phases = session.getBoss().getPhases();
        int count = 0;

        List<String> colours = List.of("RED", "BLUE", "GREEN", "CYAN", "PINK", "LIME", "MAGENTA", "YELLOW", "BROWN", "WHITE");

        items.clear();
        phases.forEach((phase -> {
            Material wool = Material.getMaterial(colours.get(count)+"_WOOL");
            if(wool==null) wool = Material.GRAY_WOOL;

            items.add(
                    MenuUtil.createButton(
                            wool,
                            Component.text(""+phase.getMaxHealthRange()),
                            List.of(Component.text("Click to manage phase "+phase.getMaxHealthRange()))
                    )
            );
        }));

        fillPages();

        pages.forEach(inventory -> {
            inventory.setItem(48,
                    MenuUtil.createButton(
                            Material.NETHER_STAR,
                            Component.text("Create phase"),
                            List.of(Component.text("Click to create a new phase"))
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
}
