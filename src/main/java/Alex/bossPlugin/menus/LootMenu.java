package Alex.bossPlugin.menus;

import Alex.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LootMenu extends Menu {
    Map<String, Object> data;
    Map<String, Object> oldData = null;
    // A copy of the original data must be kept to ensure that we can properly replace the entry when saving the data.

    enum inputEnum {
        AMOUNT,
        CHANCE
    }

    public LootMenu(Player player, MenuSession session) {
        super(player, session);
        data = new HashMap<>();
    }

    public LootMenu(Player player, MenuSession session, Map<String, Object> data) {
        super(player, session);
        if(!data.containsKey("item")) {
            Map<String, Object> map = new HashMap<>();
            map.put("item", Material.GOLD_NUGGET);
            this.data = map;
        } else {
            this.data = new HashMap<>(data);
            this.oldData = data;
        }

    }

    @Override
    public void handleClick(int slot) {
        switch(slot) {
            case 1 -> {
                preTextInput();
                player.sendMessage("Please enter the amount of items to drop");
                currentInput = inputEnum.AMOUNT;
            }
            case 2 -> {
                preTextInput();
                player.sendMessage("Please enter the chance of the item dropping");
                player.sendMessage("Please ensure it is below 1, for example 0.5 would be a one in two chance");
                currentInput = inputEnum.CHANCE;
            }
            case 25 -> {
                List<Map<String, Object>> lootList = session.getBoss().getLootList();
                boolean itemExists = false;
                if(lootList!=null&&!lootList.isEmpty()) {
                    for (int i = 0; i < lootList.size(); i++) {
                        if (lootList.get(i).equals(oldData)) {
                            lootList.remove(i);
                            lootList.add(i, data);
                            itemExists = true;
                        }
                    }
                } else {
                    lootList = new ArrayList<>();
                }
                if (!itemExists) {
                    lootList.add(data);
                }
                session.getBoss().setLootList(lootList);
                session.openLastMenu();
            }
            case 19 -> {
                session.openLastMenu();
            }
            case 22 -> {
                List<Map<String, Object>> lootList = session.getBoss().getLootList();
                for(int i=0;i<lootList.size();i++) {
                    if(lootList.get(i).equals(oldData)) {
                        lootList.remove(i);
                    }
                }
                session.openLastMenu();
            }
        }
    }

    @Override
    public void handleTextInput(String input) {
        switch((inputEnum) currentInput) {
            case AMOUNT -> {
                int amount;
                try {
                    amount = Integer.parseInt(input);
                    if(amount>0) {
                        if(data.containsKey("amount")) {
                            data.replace("amount", amount);
                        } else {
                            data.put("amount", amount);
                        }
                        openSelf();
                    } else {
                        player.sendMessage("Please ensure the amount is above 0");
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage("Please enter a valid number");
                }
            }
            case CHANCE -> {
                double chance;
                try {
                    chance = Double.parseDouble(input);
                    if(chance>0&&chance<=1) {
                        if(data.containsKey("chance")) {
                            data.replace("chance", chance);
                        } else {
                            data.put("chance", chance);
                        }
                        openSelf();
                    } else {
                        player.sendMessage("Please ensure the chance is above 0 and under or equal 1");
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage("Please enter a valid number");
                }
            }
        }
    }

    @Override
    public void openSelf() {
        menu = Bukkit.createInventory(player, 27);

        menu.setItem(0,
            MenuUtil.createButton(
                data.containsKey("item") ? (Material) data.get("item") : Material.GOLD_NUGGET,
                Component.text(data.containsKey("item") ? ((Material) data.get("item")).name() : "Something has gone very wrong") ,
                List.of(Component.text(data.containsKey("item") ? "This item is "+((Material) data.get("item")).name() : "Please delete this loottable item from bosses.yml"))
            )
        );

        menu.setItem(1,
            MenuUtil.createButton(
                Material.CHEST,
                Component.text("Change the loot item amount"),
                List.of(
                    Component.text(data.containsKey("amount") ? data.get("amount") + " items will drop." : "No amount set")
                )
            )
        );
        menu.setItem(2,
            MenuUtil.createButton(
                Material.COMPASS,
                Component.text("Change the loot item chance to drop"),
                List.of(
                    Component.text(data.containsKey("chance") ? "The chance of this item dropping is "+data.get("chance")+"." : "No chance set")
                )
            )
        );
        menu.setItem(25,
            MenuUtil.createButton(
                Material.NETHER_STAR,
                Component.text("Save this loot item"),
                List.of(Component.text("Click to save this loot item to the loot table"))
            )
        );
        menu.setItem(19,
            MenuUtil.createButton(
                Material.CRYING_OBSIDIAN,
                Component.text("Discard changes to this loot item"),
                List.of(Component.text("Click to disregard changes"))
            )
        );
        menu.setItem(22,
            MenuUtil.createButton(
                Material.TNT,
                Component.text("Delete this item"),
                List.of(Component.text("Click to delete this item from the loot table"))
            )
        );

        player.openInventory(menu);
    }
}
