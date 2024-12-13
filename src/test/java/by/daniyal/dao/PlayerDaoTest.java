package by.daniyal.dao;

import by.daniyal.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class PlayerDaoTest {

    private static final Player defaultPlayerWithoutId = new Player("Daniyal");
    private static final Player defaultPlayerWithId = new Player(1, "Eva");

    private static PlayerDao playerDao;

    @BeforeAll
    static void prepare() {
        playerDao = PlayerDao.INSTANCE;
    }

    @Test
    void saveLoadPlayerToDB() {
        Player savedPlayer = playerDao.save(defaultPlayerWithId);
        assertNotNull(savedPlayer);

        Optional<Player> player = playerDao.find(savedPlayer.getId());
        assertTrue(player.isPresent());
        assertEquals(defaultPlayerWithId, player.get());
    }

    @Test
    void update() {
        Player savedPlayer = playerDao.save(defaultPlayerWithId);

        Player updated = Player.builder()
                .id(savedPlayer.getId())
                .name("Liza")
                .build();

        playerDao.update(updated);
        assertEquals(updated.getName(), savedPlayer.getName());
        assertEquals(updated.getId(), savedPlayer.getId());
    }

    @Test
    void delete() {
        Player savedPlayer = playerDao.save(defaultPlayerWithoutId);
        assertNotNull(savedPlayer);
        boolean isDeleted = playerDao.delete(savedPlayer);
        assertTrue(isDeleted);
        assertThrows(NoSuchElementException.class, () -> playerDao.find(savedPlayer.getId()).get());
    }

    @Test
    void find() {
    }

    @AfterEach
    void init() {
        playerDao.deleteAll();
    }
}