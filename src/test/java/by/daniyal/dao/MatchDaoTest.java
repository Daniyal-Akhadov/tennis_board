package by.daniyal.dao;

import by.daniyal.entity.Match;
import by.daniyal.entity.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class MatchDaoTest {

    private static final Player FIRST_PLAYER = new Player("Daniyal");
    private static final Player SECOND_PLAYER = new Player("Shamil");
    private static final Match DEFAULT_MATCH_WITH_ID = new Match(
            1,
            new Player(1, "Daniyal"),
            new Player(2, "Shamil"),
            new Player(3, "Daniyal")
    );
    private static final Match DEFAULT_MATCH_WITHOUT_ID = new Match(
            FIRST_PLAYER, SECOND_PLAYER, FIRST_PLAYER
    );

    private static MatchDao matchDao;
    private static PlayerDao playerDao;
    private Player firstSavedPlayer;
    private Player secondSavedPlayer;


    @BeforeAll
    static void init() {
        matchDao = MatchDao.INSTANCE;
        playerDao = PlayerDao.INSTANCE;
    }

    @BeforeEach
    void prepare() {
        matchDao.deleteAll();
        playerDao.deleteAll();

        firstSavedPlayer = playerDao.save(FIRST_PLAYER);
        secondSavedPlayer = playerDao.save(SECOND_PLAYER);

        playerDao.find(firstSavedPlayer.getName())
                .ifPresentOrElse(player -> firstSavedPlayer = player,
                        () -> {
                            throw new NullPointerException("Player not found");
                        });

        playerDao.find(secondSavedPlayer.getName())
                .ifPresentOrElse(player -> secondSavedPlayer = player,
                        () -> {
                            throw new NullPointerException("Player not found");
                        });

    }

    @Test
    void saveLoadPlayerToDB() {
        Match match = new Match(firstSavedPlayer, secondSavedPlayer, firstSavedPlayer);
        Match savedMatch = matchDao.save(match);
        assertNotNull(savedMatch);


        Optional<Match> findMatch = matchDao.find(savedMatch.getId());
        assertTrue(findMatch.isPresent());
        assertEquals(DEFAULT_MATCH_WITH_ID, findMatch.get());
    }

    @Test
    void update() {
        Match match = new Match(firstSavedPlayer, secondSavedPlayer, firstSavedPlayer);
        Match savedMatch = matchDao.save(match);
        Player newPlayerForMatch = playerDao.save(new Player("Nikita"));

        Match updatedMatch = Match.builder()
                .id(savedMatch.getId())
                .firstPlayer(savedMatch.getFirstPlayer())
                .secondPlayer(newPlayerForMatch)
                .winner(savedMatch.getWinner()).build();

        boolean isUpdated = matchDao.update(updatedMatch);
        assertTrue(isUpdated);
        assertEquals(newPlayerForMatch, matchDao.find(savedMatch.getId()).get().getSecondPlayer());
    }

    @Test
    void delete() {
        Match match = new Match(firstSavedPlayer, secondSavedPlayer, firstSavedPlayer);
        Match savedMatch = matchDao.save(match);
        assertNotNull(savedMatch);
        boolean isDeleted = matchDao.delete(savedMatch);
        assertTrue(isDeleted);
        System.out.println(savedMatch);
        System.out.println(savedMatch.getId());
        assertThrows(SQLException.class, () -> matchDao.find(savedMatch.getId()));
    }

    @Test
    void find() {
        Match match = new Match(firstSavedPlayer, secondSavedPlayer, firstSavedPlayer);
        Match savedMatch = matchDao.save(match);
        System.out.println(savedMatch);
        Optional<Match> findMath = matchDao.find(savedMatch.getId());
        System.out.println(findMath.get());
        assertTrue(findMath.isPresent());
    }
}