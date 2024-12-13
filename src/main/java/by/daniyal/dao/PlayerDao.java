package by.daniyal.dao;

import by.daniyal.entity.Player;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class PlayerDao implements Dao<Player, Integer> {
    public static final PlayerDao INSTANCE = new PlayerDao();

    public static final String INSERT_SQL = """
            insert into player(name)
            values(?)
            """;

    public static final String SELECT_BY_ID_SQL = """
            select * from player where id = ?
            """;

    public static final String SELECT_BY_NAME_SQL = """
            select * from player where name = ?
            """;

    public static final String DELETE_ALL_SQL = """
            delete from player
            """;
    public static final String UPDATE_SQL = """
            update player
            set name=?
            where id=?
            """;
    private static final String DELETE_BY_ID = """
            delete from player
            where id=?
            """;

    @Override
    @SneakyThrows
    public Player save(Player model) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, model.getName());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            return Player.builder()
                    .id(generatedKeys.getInt(1))
                    .name(model.getName())
                    .build();
        }
    }

    @Override
    @SneakyThrows
    public boolean update(Player model) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, model.getName());
            statement.setInt(2, model.getId());
            return statement.executeUpdate() > 0;
        }
    }

    @Override
    @SneakyThrows
    public boolean delete(Player model) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setInt(1, model.getId());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    public void deleteAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(DELETE_ALL_SQL)) {
            statement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public Optional<Player> find(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Player result = null;

            if (resultSet.next()) {
                result = new Player(resultSet.getInt("id"), resultSet.getString("name"));
            }
            return Optional.ofNullable(result);
        }
    }

    @SneakyThrows
    public Optional<Player> find(String name) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME_SQL)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            Player result = null;

            if (resultSet.next()) {
                result = new Player(resultSet.getInt("id"), resultSet.getString("name"));
            }
            return Optional.ofNullable(result);
        }
    }
}
