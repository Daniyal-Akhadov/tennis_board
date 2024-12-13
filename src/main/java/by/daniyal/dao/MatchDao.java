package by.daniyal.dao;

import by.daniyal.entity.Match;
import by.daniyal.entity.Player;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchDao implements Dao<Match, Integer> {
    public static final String UPDATE_SQL = """
            update match set
            player1 = ?,
            player2 = ?,
            winner = ?
            where id = ?
            """;
    public static final String DELETE_SQL = "delete from match where id = ?";
    public static volatile MatchDao INSTANCE = new MatchDao();

    private static final String SAVE_SQL = """
            insert into match (player1, player2, winner)
            values (?, ?, ?)
            """;

    public static final String SELECT_BY_ID = """
            select m.id as match_id,
                   p.id as player1_id,
                   p.name as player1_name,
                   p2.id as player2_id,
                   p2.name as player2_name,
                   w.id as winner_id,
                   w.name as winner_name
            from match m
                     join player p on p.id = m.player1
                     join player p2 on p2.id = m.player2
                     join player w on w.id = m.winner
            where m.id = ?;
            """;

    @Override
    @SneakyThrows
    public Match save(Match model) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, model.getFirstPlayer().getId());
            preparedStatement.setInt(2, model.getSecondPlayer().getId());
            preparedStatement.setInt(3, model.getWinner().getId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();

            Match build = Match.builder()
                    .id(generatedKeys.getInt(1))
                    .firstPlayer(model.getFirstPlayer())
                    .secondPlayer(model.getSecondPlayer())
                    .winner(model.getWinner())
                    .build();

            System.out.println(build);
            return build;
        }
    }

    @Override
    @SneakyThrows
    public boolean update(final Match model) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setInt(1, model.getFirstPlayer().getId());
            preparedStatement.setInt(2, model.getSecondPlayer().getId());
            preparedStatement.setInt(3, model.getWinner().getId());
            preparedStatement.setInt(4, model.getId());
            final int updatedRowsCount = preparedStatement.executeUpdate();
            return updatedRowsCount > 0;
        }
    }

    @Override
    @SneakyThrows
    public boolean delete(final Match model) {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, model.getId());
            final int deletedRowsCount = preparedStatement.executeUpdate();
            return deletedRowsCount > 0;
        }
    }

    @Override
    @SneakyThrows
    public Optional<Match> find(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Optional.of(new Match(
                    resultSet.getInt("match_id"),
                    new Player(
                            resultSet.getInt("player1_id"),
                            resultSet.getString("player1_name")
                    ),
                    new Player(
                            resultSet.getInt("player2_id"),
                            resultSet.getString("player2_name")
                    ),
                    new Player(
                            resultSet.getInt("winner_id"),
                            resultSet.getString("winner_name")
                    )
            ));
        }
    }

    @SneakyThrows
    public void deleteAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from match;")) {
            preparedStatement.executeUpdate();
        }
    }
}

