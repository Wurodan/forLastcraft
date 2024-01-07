package me.wurodan.testplugin.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.concurrent.CompletableFuture;

public class PlayerLoader {

    private final String host, password, user, data;
    private final HikariDataSource ds;
    private Connection connection = null;

    public PlayerLoader(String host, String password, String user, String data) {
        this.host = host;
        this.password = password;
        this.user = user;
        this.data = data;
        this.ds = getDataSource();
        initTables();
    }

    public CompletableFuture<Integer> getMoney(String playerName) {
        return CompletableFuture.supplyAsync(() -> {
            int amount = 0;
            String selectQuery = "SELECT `money` FROM `players` WHERE `player_name` = ?";
            try (PreparedStatement selectStatement = getConnection().prepareStatement(selectQuery)) {
                selectStatement.setString(1, playerName);
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        amount = resultSet.getInt("money");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Error in getMoney");
            }
            return amount;
        });
    }

    public void addMoney(String playerName, int money) {
        CompletableFuture.runAsync(() -> {
            try {
                Connection connection = getConnection();

                String existsQuery = "SELECT 1 FROM players WHERE player_name = ?";
                String insertQuery = "INSERT INTO players (player_name, money) VALUES (?, ?)";
                String updateQuery = "UPDATE players SET money = money + ? WHERE player_name = ?";

                boolean playerExists;
                try (PreparedStatement existsStatement = connection.prepareStatement(existsQuery)) {
                    existsStatement.setString(1, playerName);
                    try (ResultSet resultSet = existsStatement.executeQuery()) {
                        playerExists = resultSet.next();
                    }
                }

                if (playerExists) {
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setInt(1, money);
                        updateStatement.setString(2, playerName);
                        updateStatement.executeUpdate();
                    }
                } else {
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setString(1, playerName);
                        insertStatement.setInt(2, money);
                        insertStatement.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Error in addMoney");
            }
        });
    }

    private void initTables() {
        CompletableFuture.runAsync(() -> {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS players (" +
                    "`id` INT AUTO_INCREMENT PRIMARY KEY," +
                    "`player_name` VARCHAR(16) NOT NULL," +
                    "`money` INT NOT NULL)";
            try (Statement statement = getConnection().createStatement()) {
                statement.execute(createTableQuery);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Error in initTables");
            }
        });
    }

    public Connection getConnection() {
        refreshConnection();
        return connection;
    }

    protected void refreshConnection() {
        try {
            if (connection != null && !connection.isClosed() && connection.isValid(1000)) {
                return;
            }

            this.connection = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("mysql error!");
        }
    }

    private HikariDataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":3306/" + data);
        config.setUsername(user);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        return new HikariDataSource(config);
    }
}
