package me.wurodan.testplugin.models;

import me.wurodan.testplugin.TestPlugin;

import java.util.concurrent.CompletableFuture;

public class DataPlayer {
    private final String name;

    public DataPlayer(String name) {
        this.name = name;
    }

    public void addMoney(int amount) {
        TestPlugin.getPlayerLoader().addMoney(name, amount);
    }

    public int getMoney() {
        CompletableFuture<Integer> futureAmount = TestPlugin.getPlayerLoader().getMoney(name);
        try {
            return futureAmount.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting amount for player: " + name);
        }
    }
}
