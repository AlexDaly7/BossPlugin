package Slippy.bossPlugin.commands;

import Slippy.bossPlugin.abilities.AbilityType;
import Slippy.bossPlugin.bosses.BossManager;
import Slippy.bossPlugin.menus.MenuSession;
import Slippy.bossPlugin.util.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BossPluginCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        switch(args[0]) {
            case "reloadConfig" -> {
                BossManager.stop();
                sender.sendMessage("Reloading config");
                ConfigUtil.createConfig();
                BossManager.loadBosses(ConfigUtil.getBosses());

                BossManager.start();
                return true;
            }
            case "menu" -> {
                if(sender instanceof Player player) {
                    if (player.isOp()) {
                        MenuSession.addSession(player);
                        return true;
                    }
                }
                return false;

            }
            default -> {
                sender.sendMessage("Please enter a command");
                return false;
            }

        }



    }

}
