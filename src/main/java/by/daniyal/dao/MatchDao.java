package by.daniyal.dao;

import by.daniyal.model.Match;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchDao implements Dao<Match, Integer> {
    private static volatile MatchDao Instance;

    public synchronized static MatchDao getInstance() {
        if (Instance == null) {
            Instance = new MatchDao();
        }

        return Instance;
    }

    @Override
    public Match save(Match model) {
        return null;
    }

    @Override
    public Match update(Match model) {
        return null;
    }

    @Override
    public Match delete(Match model) {
        return null;
    }

    @Override
    public Match find(Integer id) {
        return null;
    }
}

