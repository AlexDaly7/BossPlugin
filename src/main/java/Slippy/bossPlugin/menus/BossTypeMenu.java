package Slippy.bossPlugin.menus;

import Slippy.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BossTypeMenu extends Menu {
    Inventory secondMenu;
    List<EntityType> mobs;
    int page = 0;

    public BossTypeMenu(Player player, MenuSession session) {
        super(player, session);
        menu = Bukkit.createInventory(player, 54, "Set mob type");
        secondMenu = Bukkit.createInventory(player, 54, "Set mob type");
        mobs = Arrays.stream(EntityType.values())
                .filter(type -> type.getEntityClass()!=null)
                .filter(type -> Mob.class.isAssignableFrom(type.getEntityClass()))
                .collect(Collectors.toList());
        for(int i=0;i<mobs.size();i++) {
            ItemStack item = MenuUtil.createButton(Material.BAT_SPAWN_EGG,
                    Component.text(mobs.get(i).getName()),
                    List.of(Component.text(mobs.get(i).getKey().toString()))
            );
            if(i<=44) {
                menu.setItem(i, item);
            } else {
                secondMenu.setItem(i-45, item);
            }
        }
        menu.setItem(52,
                MenuUtil.createButton(
                        Material.COMMAND_BLOCK,
                        Component.text("Go to next page"),
                        List.of(Component.text("Click to go to next page"))
                )
        );
        secondMenu.setItem(46,
                MenuUtil.createButton(
                        Material.COMMAND_BLOCK,
                        Component.text("Go to previous page"),
                        List.of(Component.text("Click to go to previous page"))
                )
        );
        ItemStack backBtn = MenuUtil.createButton(
                Material.STRUCTURE_BLOCK,
                Component.text("Return to boss creation menu"),
                List.of(Component.text("Click to go back to the boss creation menu"))
        );
        menu.setItem(49, backBtn);
        secondMenu.setItem(49, backBtn);

    }

    @Override
    public void handleClick(int slot) {
        if(slot==49) {
            if(page==0) menu.close();
            if(page==1) secondMenu.close();
            session.openMenu(new BossCreationMenu(player, session));
            return;
        }
        if(page==0) {
            if(slot==52) {
                menu.close();
                player.openInventory(secondMenu);
                page = 1;
            } else if(slot<45){
                session.getBoss().setEntityType(mobs.get(slot));
                menu.close();
                session.openMenu(new BossCreationMenu(player, session));
            }
        } else if(page==1) {
            if(slot==46) {
                secondMenu.close();
                player.openInventory(menu);
                page = 0;
            } else if(slot<45){
                session.getBoss().setEntityType(mobs.get(slot+45));
                secondMenu.close();
                session.openMenu(new BossCreationMenu(player, session));
            }
        }
    }
}
