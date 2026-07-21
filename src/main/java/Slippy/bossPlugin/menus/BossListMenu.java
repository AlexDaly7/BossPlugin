package Slippy.bossPlugin.menus;

import Slippy.bossPlugin.bosses.BaseBoss;
import Slippy.bossPlugin.bosses.BossManager;
import Slippy.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class BossListMenu extends Menu {
    List<BaseBoss> bosses;

    public BossListMenu(Player player, MenuSession session) {
        super(player, session);
        bosses = BossManager.getBosses();
        menu = Bukkit.createInventory(player, 45, "Boss List");
        for(int i=0;i<bosses.size();i++){
            menu.setItem(i,
                    MenuUtil.createButton(
                            Material.SPAWNER,
                            Component.text(bosses.get(i).getName()),
                            List.of(Component.text("Cool boss"))
                    )
            );
        }
    }

    @Override
    public void handleClick(int slot) {

    }
}
