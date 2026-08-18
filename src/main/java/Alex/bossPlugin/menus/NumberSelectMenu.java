package Alex.bossPlugin.menus;

import Alex.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class NumberSelectMenu extends Menu {
    private int number = 0;

    public NumberSelectMenu(Player player, MenuSession session) {
        super(player, session);
        menu = Bukkit.createInventory(player, 27, "Number Selection");
        menu.setItem(4,
            MenuUtil.createButton(
                Material.COMMAND_BLOCK,
                Component.text(number),
                List.of(Component.text("Currently selected number is "+number))
            )
        );
        menu.setItem(3,
                MenuUtil.createButton(
                        Material.RED_CONCRETE,
                        Component.text("Decrease by 1"),
                        List.of(Component.text("Decrease the currently selected number by 1"))
                )
        );
        menu.setItem(2,
                MenuUtil.createButton(
                        Material.RED_WOOL,
                        Component.text("Decrease by 5"),
                        List.of(Component.text("Decrease the currently selected number by 5"))
                )
        );
        menu.setItem(1,
                MenuUtil.createButton(
                        Material.REDSTONE_BLOCK,
                        Component.text("Decrease by 10"),
                        List.of(Component.text("Decrease the currently selected number by 10"))
                )
        );

        menu.setItem(5,
                MenuUtil.createButton(
                        Material.GREEN_CONCRETE,
                        Component.text("Increase by 1"),
                        List.of(Component.text("Increase the currently selected number by 1"))
                )
        );
        menu.setItem(6,
                MenuUtil.createButton(
                        Material.GREEN_WOOL,
                        Component.text("Increase by 5"),
                        List.of(Component.text("Increase the currently selected number by 5"))
                )
        );
        menu.setItem(7,
                MenuUtil.createButton(
                        Material.MOSS_BLOCK,
                        Component.text("Increase by 10"),
                        List.of(Component.text("Increase the currently selected number by 10"))
                )
        );

        menu.setItem(24,
                MenuUtil.createButton(
                        Material.END_CRYSTAL,
                        Component.text("Save change"),
                        List.of(Component.text("Save changed value and return to previous menu"))
                )
        );
        menu.setItem(20,
                MenuUtil.createButton(
                        Material.TNT,
                        Component.text("Return to previous menu"),
                        List.of(Component.text("Disregard changes and return to the previous menu"))
                )
        );
    }

    @Override
    public void handleClick(int slot) {
        switch(slot) {
            case 3 -> number-=1;
            case 2 -> number-=5;
            case 1 -> number-=10;

            case 5 -> number+=1;
            case 6 -> number+=5;
            case 7 -> number+=10;

            case 20 -> session.openLastMenu();
            // TODO: Make it so number select can pass back values
            //case 24 ->
        }
        if(number<0) {
            number = 0;
        }
        menu.setItem(4,
                MenuUtil.createButton(
                        Material.COMMAND_BLOCK,
                        Component.text(number),
                        List.of(Component.text("Currently selected number is "+number))
                )
        );
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

}
