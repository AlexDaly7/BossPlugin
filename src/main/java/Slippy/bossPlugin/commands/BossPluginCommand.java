package Slippy.bossPlugin.commands;

import Slippy.bossPlugin.bosses.BossManager;
import Slippy.bossPlugin.util.ConfigUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
            default -> {
                sender.sendMessage("Please enter a command");
                return false;
            }

        }



    }

}
