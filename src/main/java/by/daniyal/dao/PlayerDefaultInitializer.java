package by.daniyal.dao;

import by.daniyal.entity.Player;
import by.daniyal.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class PlayerDefaultInitializer implements Initializer {
    private static PlayerDefaultInitializer INSTANCE;

    public static PlayerDefaultInitializer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerDefaultInitializer();
        }

        return INSTANCE;
    }

    private final List<Player> players;

    private PlayerDefaultInitializer() {
        players = new ArrayList<>();
        addDefaultPlayers();
    }

    public void initialize() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        for (Player player : players) {
            session.save(player);
        }

        session.getTransaction().commit();
    }

    public Player getPlayer(int id) {
        if (id < 0 || id >= players.size()) {
            throw new IndexOutOfBoundsException("Invalid id: " + id);
        }

        return players.get(id);
    }

    public int size() {
        return players.size();
    }

    private void addDefaultPlayers() {
        players.add(new Player(1, "d. akh"));
        players.add(new Player(2, "d. haf"));
        players.add(new Player(3, "n. sul"));
        players.add(new Player(4, "m. mal"));
        players.add(new Player(5, "d. efr"));
        players.add(new Player(6, "d. dia"));
        players.add(new Player(7, "e. dan"));
        players.add(new Player(8, "l. eli"));
        players.add(new Player(9, "s. bry"));
        players.add(new Player(10, "n. kos"));
        players.add(new Player(11, "e. kol"));
        players.add(new Player(12, "m. smi"));
        players.add(new Player(13, "l. mao"));
        players.add(new Player(14, "s. kar"));
        players.add(new Player(15, "a. nal"));
        players.add(new Player(16, "m. mar"));
        players.add(new Player(17, "r. akh"));
        players.add(new Player(18, "r. sak"));
        players.add(new Player(19, "m. mav"));
        players.add(new Player(20, "p. kor"));
        players.add(new Player(21, "s. mer"));
        players.add(new Player(22, "a. aru"));
        players.add(new Player(23, "s. shl"));
        players.add(new Player(23, "e. sha"));
    }
}

