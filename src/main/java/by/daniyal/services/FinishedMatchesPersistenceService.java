package by.daniyal.services;

import by.daniyal.dao.MatchDao;
import by.daniyal.entity.Match;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FinishedMatchesPersistenceService {
    public static final FinishedMatchesPersistenceService INSTANCE = new FinishedMatchesPersistenceService();

    private final MatchDao matchDao = MatchDao.INSTANCE;

    public void save(final Match match) {
        matchDao.save(match);
    }
}
