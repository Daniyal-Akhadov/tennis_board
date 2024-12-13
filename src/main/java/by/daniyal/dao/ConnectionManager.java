package by.daniyal.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.sql.Connection;

@UtilityClass
public final class ConnectionManager {
    private static final HikariDataSource hikariDataSource;
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/tennis_board";

    static {
        HikariConfig config = hikariConfig();
        hikariDataSource = new HikariDataSource(config);
    }

    @SneakyThrows
    public static Connection get() {
        return hikariDataSource.getConnection();
    }

    private static HikariConfig hikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(DRIVER);
        config.setJdbcUrl(URL);
        config.setUsername("postgres");
        config.setPassword("postgres");
        return config;
    }
}
