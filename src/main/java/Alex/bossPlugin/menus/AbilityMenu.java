package Alex.bossPlugin.menus;

import Alex.bossPlugin.abilities.Ability;
import Alex.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbilityMenu extends Menu {
    Ability ability;
    Map<String, Object> data;
    List<String> abilities = new ArrayList<String>();
    boolean isSpecial = false;
    String currentData;

    public AbilityMenu(Player player, MenuSession session, Ability ability, boolean isSpecial) {
        super(player, session);
        this.ability = ability;
        this.data = ability.getData();
        this.isSpecial = isSpecial;
    }

    @Override
    public void handleClick(int slot) {
        if(slot<abilities.size()) {
            preTextInput();
            currentData = abilities.get(slot);
            player.sendMessage("Please enter a value for "+currentData);
            menu.close();
        }
        switch(slot) {
            case 19 -> {
                session.openLastMenu();
            }
            case 22 -> {
                List<Ability> abilityList;
                if(isSpecial) {
                    abilityList = session.getPhase().getSpecialAbilities();
                } else {
                    abilityList = session.getPhase().getBaseAbilities();
                }

                for(int i=0;i<abilityList.size();i++) {
                    if(abilityList.get(i).getName().equals(ability.getName())) {
                        abilityList.remove(i);
                    }
                }

                if(isSpecial) {
                    session.getPhase().setSpecialAbilities(abilityList);
                } else {
                    session.getPhase().setBaseAbilities(abilityList);
                }
                session.openLastMenu();
            }
            case 25 -> {
                ability.setData(data);
                List<Ability> abilityList;
                if(isSpecial) {
                    abilityList = session.getPhase().getSpecialAbilities();
                } else {
                    abilityList = session.getPhase().getBaseAbilities();
                }

                // Ensure that if the ability exists it is overwritten
                boolean abilityExists = false;
                for(int i=0;i<abilityList.size();i++) {
                    if(abilityList.get(i).getName().equals(ability.getName())) {
                        abilityExists = true;
                        abilityList.remove(i);
                        abilityList.add(i, ability);
                    }
                }

                // If ability does not exist add it to list
                if(!abilityExists) {
                    abilityList.add(ability);
                }

                if(isSpecial) {
                    session.getPhase().setSpecialAbilities(abilityList);
                } else {
                    session.getPhase().setBaseAbilities(abilityList);
                }
                session.openLastMenu();
            }
        }
    }

    @Override
    public void handleTextInput(String input) {
        int value;

        try {
            player.sendMessage("Try started");
            value = Integer.parseInt(input);
            if(data.containsKey(currentData)) {
                data.replace(currentData, value);
            } else {
                data.put(currentData, value);
            }
            openSelf();
        } catch (NumberFormatException e) {
            player.sendMessage("That is not a valid entry.");
        }
    }

    @Override
    public void openSelf() {
        menu = Bukkit.createInventory(player, 27, ability.getName()+" menu");

        menu.setItem(19,
            MenuUtil.createButton(
                Material.CRYING_OBSIDIAN,
                Component.text("Go back to previous menu"),
                List.of(Component.text("Click to go back to the previous menu"))
            )
        );

        menu.setItem(22,
                MenuUtil.createButton(
                        Material.TNT,
                        Component.text("Delete ability"),
                        List.of(Component.text("Click to delete this ability from the list"))
                )
        );

        menu.setItem(25,
            MenuUtil.createButton(
                Material.NETHER_STAR,
                Component.text("Save this loot item"),
                List.of(Component.text("Click to save this loot item to the loot table"))
            )
        );

        int[] count = {0};
        data.forEach((string, object) -> {
            if(!string.equals("ability")) {
                try {
                    menu.setItem(count[0],
                        MenuUtil.createButton(
                            Material.STONE_BRICKS,
                            Component.text(string),
                            List.of(
                                Component.text("The current value is " + object.toString() + "."),
                                Component.text("Press to edit the value of this ability")
                            )
                        )
                    );
                } catch(ClassCastException e) {
                    menu.setItem(count[0],
                        MenuUtil.createButton(
                            Material.STONE_BRICKS,
                            Component.text(string),
                            List.of(
                                Component.text("The value of this object cannot be shown."),
                                Component.text("Press to edit the value of this ability.")
                            )
                        )
                    );
                }
            }



            abilities.add(string);

            count[0]++;
        });

        player.openInventory(menu);
    }
}
