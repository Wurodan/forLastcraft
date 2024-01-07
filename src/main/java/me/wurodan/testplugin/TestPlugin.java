package me.wurodan.testplugin;


import lombok.Getter;
import me.wurodan.testplugin.commands.GiveCommand;
import me.wurodan.testplugin.commands.MoneyCommand;
import me.wurodan.testplugin.listener.JoinListener;
import me.wurodan.testplugin.sql.PlayerLoader;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestPlugin extends JavaPlugin {

    @Getter
    private static final PlayerLoader playerLoader = new PlayerLoader("localhost", "123",
            "root", "data");

    @Override
    public void onEnable() {
        getLogger().info("TestPlugin (1.0.0) started! Made by Wurodan");
        new JoinListener(this);
        new GiveCommand(this);
        new MoneyCommand(this);
    }
}
