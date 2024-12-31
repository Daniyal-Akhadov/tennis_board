package by.daniyal.entity;

import by.daniyal.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Main {

    public static void main(final String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            for (int i = 0; i < 100; i++) {
                Player player = new Player("Player " + i);
                Player player2 = new Player("Player " + i * 101);
                Match match = new Match(player, player2, null);

                session.save(player);
                session.save(player2);
                session.save(match);
            }
        }
    }
}

