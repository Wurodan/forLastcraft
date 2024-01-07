package me.wurodan.testplugin.commands;

import me.wurodan.testplugin.TestPlugin;
import me.wurodan.testplugin.models.DataPlayer;
import me.wurodan.testplugin.sql.PlayerLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GiveCommand implements CommandExecutor {


    private final PlayerLoader loader;

    public GiveCommand(PlayerLoader loader) {
        this.loader = loader;
    }

    public GiveCommand(TestPlugin plugin) {
        this(plugin.getPlayerLoader());
        plugin.getCommand("givemoney").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.hasPermission("testplugin.givemoney")) {
            commandSender.sendMessage("§cНет прав!");
            return true;
        }

        if (args.length < 2) {
            commandSender.sendMessage("§cИспользуйте /givemoney <игрок> <монеты>!");
            return true;
        }

        DataPlayer player = new DataPlayer(args[0], loader);
        player.addMoney(Integer.parseInt(args[1]));
        commandSender.sendMessage("§aУспешно!");
        return false;
    }
}
