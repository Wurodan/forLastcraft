package me.wurodan.testplugin.models;

import me.wurodan.testplugin.sql.PlayerLoader;

import java.util.concurrent.CompletableFuture;

public class DataPlayer {
    private final String name;
    private final PlayerLoader loader;

    public DataPlayer(String name, PlayerLoader loader) {
        this.name = name;
        this.loader = loader;
    }

    public void addMoney(int amount) {
        loader.addMoney(name, amount);
    }

    public CompletableFuture<Integer> getMoney() {
        return loader.getMoney(name);
    }
}
