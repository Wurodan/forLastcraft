package me.wurodan.testplugin;

import lombok.Getter;
import me.wurodan.testplugin.commands.GiveCommand;
import me.wurodan.testplugin.listener.JoinListener;
import me.wurodan.testplugin.sql.PlayerLoader;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestPlugin extends JavaPlugin {

    @Getter
    private final PlayerLoader playerLoader = new PlayerLoader("localhost", "123",
            "root", "test");

    @Override
    public void onEnable() {
        new JoinListener(this);
        new GiveCommand(this);

        getLogger().info("TestPlugin (1.0.0) started! Made by Wurodan");
    }
}
