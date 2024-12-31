package by.daniyal.dao;

import by.daniyal.entity.Player;
import by.daniyal.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerDao implements CrudDao<Player, Long> {
    public static final PlayerDao INSTANCE = new PlayerDao();

    public Optional<Player> findByName(String name) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Player result;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Player> query = session.createQuery("from Player where name = :name", Player.class);
            query.setParameter("name", name);
            query.setMaxResults(1);
            result = query.getSingleResult();
            session.getTransaction().commit();
        } catch (NoResultException exception) {
            result = null;
        }

        return Optional.ofNullable(result);
    }

    @Override
    public Player save(Player player) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(player);
        session.getTransaction().commit();
        return player;
    }
}

