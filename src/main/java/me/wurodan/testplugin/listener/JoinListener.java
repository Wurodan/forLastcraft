package me.wurodan.testplugin.listener;

import me.wurodan.testplugin.TestPlugin;
import me.wurodan.testplugin.models.DataPlayer;
import me.wurodan.testplugin.sql.PlayerLoader;
import me.wurodan.testplugin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class JoinListener implements Listener {

    private final PlayerLoader loader;
    private final TestPlugin plugin;


    public JoinListener(TestPlugin plugin, PlayerLoader loader) {
        this.plugin = plugin;
        this.loader = loader;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public JoinListener(TestPlugin plugin) {
        this(plugin, plugin.getPlayerLoader());
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        DataPlayer dataPlayer = new DataPlayer(player.getName(), loader);

        dataPlayer.getMoney().thenAcceptAsync(money -> {
            ItemStack itemStack = createPlayerItemStack(money);

            // в основном потоке выдать предмет
            Bukkit.getScheduler().runTask(plugin, () -> player.getInventory().setItem(1, itemStack));
        });

    }

    private ItemStack createPlayerItemStack(int money) {
        return new ItemBuilder(Material.STONE)
                .setDisplayName("§cВзгляни на лор")
                .setLore(Collections.singletonList("§fУ тебя: §a" + money))
                .build();
    }
}
