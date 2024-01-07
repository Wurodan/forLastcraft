package me.wurodan.testplugin.listener;

import me.wurodan.testplugin.TestPlugin;
import me.wurodan.testplugin.models.DataPlayer;
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

    public JoinListener(TestPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        DataPlayer dataPlayer = new DataPlayer(player.getName());

        player.getInventory().setItem(1, getPlayerItemStack(dataPlayer.getMoney()));
    }

    private ItemStack getPlayerItemStack(int money) {
        return new ItemBuilder(Material.STONE)
                .setDisplayName("§cВзгляни на лор")
                .setLore(Collections.singletonList("§fУ тебя: §a" + money))
                .build();
    }
}
