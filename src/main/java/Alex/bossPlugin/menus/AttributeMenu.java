package Alex.bossPlugin.menus;

import Alex.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeMenu extends Menu {
    Attribute attribute;


    public AttributeMenu(Player player, MenuSession session) {
        super(player, session);
        menu = Bukkit.createInventory(player, 54, "Attribute Menu");
    }

    @Override
    public void openSelf() {
        int count = 0;
        for(Attribute attribute : Registry.ATTRIBUTE) {
            menu.setItem(count,
                MenuUtil.createButton(
                    Material.BONE,
                    Component.text(attribute.name()),
                    List.of(Component.text("Click to change the value of this attribute"))
                )
            );
            count++;
        }
        menu.setItem(47,
                MenuUtil.createButton(
                        Material.COMMAND_BLOCK,
                        Component.text("Go back to previous page"),
                        List.of(Component.text("Click to go back to previous page"))
                )
        );
        player.openInventory(menu);
    }

    @Override
    public void handleClick(int slot) {
        if(slot<=30) {
            preTextInput();
            menu.close();
            attribute = Registry.ATTRIBUTE.stream().toList().get(slot);
            player.sendMessage("Enter a number to replace the value for the "+attribute+" attribute.");
            try {
                player.sendMessage(
                    session.getBoss().getEntityType().getDefaultAttributes().getAttribute(attribute).getDefaultValue()
                    + " is the default value for this mob"
                );
            } catch (NullPointerException e) {
                player.openInventory(menu);
            }
        }
        switch(slot) {
            case 47 -> {
                session.openLastMenu();
            }
        }
    }

    @Override
    public void handleTextInput(String input) {
        List<Map<String, Object>> attributes = session.getBoss().getAttributes();

        boolean attributeExists = false;
        try {
            double parsedInput= Double.parseDouble(input);
            if(attributes==null||attributes.isEmpty()) attributes = new ArrayList<Map<String, Object>>();
            for (int i=0;i<attributes.size();i++) {
                player.sendMessage("FromArray: "+attributes.get(i).get("attribute")+"\nFrom attribuite: "+attribute.name());
                if (((Attribute) attributes.get(i).get("attribute")).equals(attribute)) {
                    attributeExists = true;
                    attributes.get(i).remove("value");
                    attributes.get(i).put("value", parsedInput);
                }
            }
            if(!attributeExists) {
                attributes.add(Map.of(
                        "attribute", attribute.name(),
                        "value", parsedInput
                ));
            }
            session.getBoss().setAttributes(attributes);
            player.openInventory(menu);
        } catch (NumberFormatException e) {
            player.sendMessage(input+" is not a valid number, please try again.");
        }

    }
}
