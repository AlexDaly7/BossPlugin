package Alex.bossPlugin.menus;

import Alex.bossPlugin.bosses.BaseBoss;
import Alex.bossPlugin.bosses.BossManager;
import Alex.bossPlugin.config.ConfigUtil;
import Alex.bossPlugin.util.MenuUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import java.util.List;

public class BossCreationMenu extends Menu {
    private enum inputEnum {
        NAME,
        HEALTH,
        RESPAWN
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
            case 4 -> {
                preTextInput();
                player.sendMessage("Please enter the bosses max health");
                currentInput = inputEnum.HEALTH;
            }
            case 5 -> {
                preTextInput();
                player.sendMessage("Please enter the respawn time of the boss (in seconds)");
                currentInput = inputEnum.RESPAWN;
            }
            case 43 -> {
                // If boss has no id, set id and add to bosses. Otherwise, replace old boss object with updated one.

                // A check should be run here to either inform the player of missing required fields for the boss
                // or to fill in those missing fields with default values

                List<BaseBoss> bosses = BossManager.getBosses();
                if(session.getBoss().getId()==null) {
                    session.getBoss().setId(bosses.size());
                    player.sendMessage("ID: "+session.getBoss().getId());
                    ConfigUtil.saveBoss(session.getBoss());
                    BossManager.loadBosses(ConfigUtil.getBosses());
                } else {
                    for(int i=0;i<bosses.size();i++) {
                        if(bosses.get(i).getId()==session.getBoss().getId()) {
                            bosses.get(i).despawnBoss();
                            bosses.remove(i);
                            ConfigUtil.saveBoss(session.getBoss());
                            BossManager.loadBosses(ConfigUtil.getBosses());
                        }
                    }
                }

                session.openLastMenu();
            }
            case 6 -> {
                session.openMenu(new AttributeMenu(player, session));
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
            case HEALTH -> {
                int healthIn;
                try {
                    healthIn = Integer.parseInt(input);
                    session.getBoss().setHealth(healthIn);
                    openSelf();
                } catch (NumberFormatException e) {
                    player.sendMessage(input+" is not a valid number, please try again.");
                }
            }
            case RESPAWN -> {
                int respawnIn;
                try {
                    respawnIn = Integer.parseInt(input);
                    session.getBoss().setRespawnTimer(respawnIn);
                    openSelf();
                } catch (NumberFormatException e) {
                    player.sendMessage(input+" is not a valid number, please try again.");
                }
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
        menu.setItem(4,
            MenuUtil.createButton(
                Material.COOKED_BEEF,
                Component.text("Set boss health"),
                List.of(
                    Component.text("Health: "+session.getBoss().getHealth()),
                    Component.text("Click to change boss max health.")
                )
            )
        );
        menu.setItem(5,
            MenuUtil.createButton(
                Material.CLOCK,
                Component.text("Set respawn time"),
                List.of(
                    Component.text("Respawn time: "+session.getBoss().getRespawnTimer()),
                    Component.text("Click to change boss respawn time.")
                )
            )
        );
        menu.setItem(6,
            MenuUtil.createButton(
                Material.CHORUS_FLOWER,
                Component.text("Change boss attributes"),
                List.of(Component.text("Click to see a list of boss attributes"))
            )
        );
        player.openInventory(menu);
    }

}
