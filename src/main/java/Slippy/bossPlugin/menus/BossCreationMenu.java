package Slippy.bossPlugin.menus;

import Slippy.bossPlugin.bosses.BaseBoss;
import Slippy.bossPlugin.bosses.CustomBoss;
import Slippy.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
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

        if(session.getBoss()==null) {
            session.setBoss(new CustomBoss(player.getWorld(), player.getLocation()));
        }
    }

    @Override
    public void handleClick(int slot) {
        switch(slot) {
            case 0 -> {
                menu.close();
                player.sendMessage("Please enter the name of the boss");
                currentInput = inputEnum.NAME;
            }
            case 1 -> {
                session.openMenu(new BossTypeMenu(player, session));
            }
            case 2 -> {
                session.openMenu(new PhaseListMenu(player, session));
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
}
