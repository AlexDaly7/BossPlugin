package Slippy.bossPlugin.menus;

import Slippy.bossPlugin.bosses.BaseBoss;
import Slippy.bossPlugin.bosses.BossManager;
import Slippy.bossPlugin.bosses.CustomBoss;
import Slippy.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import java.util.List;

public class BossCreationMenu extends Menu {
    private enum inputEnum {
        NAME,
        MOB_TYPE
    }

    public BossCreationMenu(Player player, MenuSession session) {
        super(player, session);

        menu = Bukkit.createInventory(player, 45, "Boss Creation");
    }

    @Override
    public void handleClick(int slot) {
        switch(slot) {
            case 0 -> {
                preTextInput();
                player.sendMessage("Please enter the name of the boss");
                currentInput = inputEnum.NAME;
            }
            case 1 -> {
                session.openMenu(new BossTypeMenu(player, session));
            }
            case 2 -> {
                session.openMenu(new PhaseListMenu(player, session));
            }
            case 3 -> {
                session.openMenu(new LocationMenu(player, session));
            }
            case 43 -> {
                // If boss has no id, set id and add to bosses. Otherwise, replace old boss object with updated one.
                List<BaseBoss> bosses = BossManager.getBosses();
                if(session.getBoss().getId()==null) {
                    session.getBoss().setId(bosses.size());
                    player.sendMessage("ID: "+session.getBoss().getId());
                    BossManager.add(session.getBoss());
                } else {
                    for(int i=0;i<bosses.size();i++) {
                        player.sendMessage(bosses.get(i).getId().toString());
                        if(bosses.get(i).getId()==session.getBoss().getId()) {
                            bosses.remove(i);
                            bosses.add(i, session.getBoss());
                        }
                    }
                }
                session.openLastMenu();
            }
        }
    }

    @Override
    public void handleTextInput(String input) {
        switch((inputEnum) currentInput) {
            case NAME -> {
                session.getBoss().setName(input);
                openSelf();
            }
        }

    }

    @Override
    public void openSelf() {
        menu.setItem(0,
            MenuUtil.createButton(
                Material.SPAWNER,
                Component.text("Set boss name"),
                List.of(Component.text("Click to set boss name"))
            )
        );
        menu.setItem(1,
            MenuUtil.createButton(
                Material.CREEPER_HEAD,
                Component.text("Set boss mob type"),
                List.of(Component.text("Click to set boss mob type"))
            )
        );
        menu.setItem(2,
            MenuUtil.createButton(
                Material.BONE,
                Component.text("Manage Boss Phases"),
                List.of(Component.text("Click to open the bosses phase menu"))
            )
        );
        menu.setItem(43,
            MenuUtil.createButton(
                Material.ENDER_EYE,
                Component.text("Save Changes"),
                List.of(Component.text("Save current changes and return to previous menu"))
            )
        );
        Location loc = session.getBoss().getSpawnLoc();
        menu.setItem(3,
            MenuUtil.createButton(
                Material.COMMAND_BLOCK,
                Component.text("Boss co-ordinate menu"),
                List.of(
                    Component.text("X: "+(int)loc.getX()),
                    Component.text("Y: "+(int)loc.getY()),
                    Component.text("Z: "+(int)loc.getZ())
                )
            )
        );
        player.openInventory(menu);
    }

}
