package Alex.bossPlugin.menus;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BossTypeMenu extends MultiPageMenu {
    List<EntityType> mobs;

    public BossTypeMenu(Player player, MenuSession session) {
        super(player, session);
        menuName = "Boss Type Selection";
        // Get all EntityTypes that are also mobs
        mobs = Arrays.stream(EntityType.values())
            .filter(type -> type.getEntityClass()!=null)
            .filter(type -> Mob.class.isAssignableFrom(type.getEntityClass()))
            .collect(Collectors.toList());

        for(int i=0;i<mobs.size();i++) {
            // Attempt to find spawn egg of mob type, if not set item to spawner
            ItemStack itemStack;
            try {
                itemStack = new ItemStack(Material.getMaterial(mobs.get(i).name().toString()+"_SPAWN_EGG"));
            } catch(IllegalArgumentException e) {
                itemStack = new ItemStack(Material.SPAWNER);
            }
            ItemMeta meta = itemStack.getItemMeta();
            meta.displayName(Component.text(mobs.get(i).name()));
            itemStack.setItemMeta(meta);

            items.add(itemStack);
        }
        fillPages();
    }

    @Override
    public void handleClick(int slot) {
        if(pageChangeClick(slot)) return;

        session.getBoss().setEntityType(mobs.get(slot+(currentPage*45)));
        session.openLastMenu();
    }
}
