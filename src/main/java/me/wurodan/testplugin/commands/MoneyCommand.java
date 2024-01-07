package me.wurodan.testplugin.commands;

import me.wurodan.testplugin.TestPlugin;
import me.wurodan.testplugin.models.DataPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class MoneyCommand implements CommandExecutor {


    public MoneyCommand(TestPlugin plugin) {
        plugin.getCommand("money").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof ConsoleCommandSender) {
            commandSender.sendMessage("§cЭта команда только для игроков!");
            return true;
        }

        DataPlayer player = new DataPlayer(commandSender.getName());
        commandSender.sendMessage("§fВаш баланс: §a" + player.getMoney());
        return false;
    }
}
