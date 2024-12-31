package by.daniyal.services;

import by.daniyal.entity.Match;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {

    public static final OngoingMatchesService INSTANCE = new OngoingMatchesService();

    private final Map<UUID, Match> matches = new ConcurrentHashMap<>();

    private OngoingMatchesService() {
    }

    public void save(UUID uuid, Match match) {
        matches.put(uuid, match);
    }

    public Optional<Match> find(UUID uuid) {
        Match match = matches.get(uuid);
        return Optional.of(match);
    }

    public void remove(Match match) {
        if (match != null) {
            matches.remove(match);
        } else {
            throw new IllegalArgumentException("match is null");
        }
    }
}
