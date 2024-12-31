package by.daniyal.dao;

import by.daniyal.entity.Match;
import by.daniyal.entity.Player;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchDefaultInitializer implements Initializer {
    private static MatchDefaultInitializer INSTANCE;

    public static MatchDefaultInitializer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MatchDefaultInitializer();
        }

        return INSTANCE;
    }

    private final PlayerDefaultInitializer playerDefaultInitializer = PlayerDefaultInitializer.getInstance();
    private final List<Match> matches = new ArrayList<>();

    @Override
    public void initialize() {
        final int skipEveryTwoPlayers = 2;
        final int playersCount = playerDefaultInitializer.size();
        final Random random = new Random();

        for (int i = 0; i < playersCount - 1; i += skipEveryTwoPlayers) {
            Player first = playerDefaultInitializer.getPlayer(i);
            Player second = playerDefaultInitializer.getPlayer(i + 1);
            Player defineWinner = random.nextBoolean() ? first : second;
            Match match = new Match(first, second, defineWinner);
            matches.add(match);
        }
    }

    public List<Match> getMatches() {
        return new ArrayList<>(matches);
    }

    public Match getMatch(int index) {
        return matches.get(index);
    }

    public int size() {
        return matches.size();
    }
}
