package Slippy.bossPlugin.menus;

import Slippy.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class LocationMenu extends Menu {
    Location loc;
    private enum inputEnum {
        X,
        Y,
        Z
    }

    public LocationMenu(Player player, MenuSession session) {
        super(player, session);
        menu = Bukkit.createInventory(player, 27, "Location Menu");
        if(session.getBoss().getSpawnLoc()!=null) {
            loc = session.getBoss().getSpawnLoc();
        } else {
            loc = new Location(session.getBoss().getWorld(), 0, 0, 0);

        }
    }


    @Override
    public void openSelf() {
        menu.setItem(3,
            MenuUtil.createButton(
                Material.RED_WOOL,
                Component.text("X: "+loc.getX()),
                List.of(Component.text("Click to change X co-ordinate"))
            )
        );
        menu.setItem(4,
                MenuUtil.createButton(
                        Material.GREEN_WOOL,
                        Component.text("Y: "+loc.getY()),
                        List.of(Component.text("Click to change Y co-ordinate"))
                )
        );
        menu.setItem(5,
                MenuUtil.createButton(
                        Material.BLUE_WOOL,
                        Component.text("Z: "+loc.getZ()),
                        List.of(Component.text("Click to change Z co-ordinate"))
                )
        );
        menu.setItem(20,
                MenuUtil.createButton(
                        Material.CRYING_OBSIDIAN,
                        Component.text("Go back to previous menu"),
                        List.of(Component.text("Click to go back to the previous menu"))
                )
        );


        player.openInventory(menu);
    }

    @Override
    public void handleClick(int slot) {
        switch(slot) {
            case 3 -> {
                preTextInput();
                player.sendMessage("Please enter the X co-ordinate");
                currentInput = inputEnum.X;
            }
            case 4 -> {
                preTextInput();
                player.sendMessage("Please enter the Y co-ordinate");
                currentInput = inputEnum.Y;
            }
            case 5 -> {
                preTextInput();
                player.sendMessage("Please enter the Z co-ordinate");
                currentInput = inputEnum.Z;
            }
            case 20 -> {
                session.openLastMenu();
            }
        }
        session.getBoss().setSpawnLoc(loc);

    }

    @Override
    public void handleTextInput(String input) {
        double cord;
        try {
            cord = Double.parseDouble(input);
            switch((inputEnum) currentInput) {
                case X -> {
                    loc.setX(cord);
                    openSelf();
                }
                case Y -> {
                    loc.setY(cord);
                    openSelf();
                }
                case Z -> {
                    loc.setZ(cord);
                    openSelf();
                }
            }
        } catch(NumberFormatException e) {
            player.sendMessage("That is an invalid number.");
        }
    }
}
