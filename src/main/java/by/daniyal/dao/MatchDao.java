package by.daniyal.dao;

import by.daniyal.entity.Match;
import by.daniyal.entity.PageRequest;
import by.daniyal.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchDao {

    public static final MatchDao INSTANCE = new MatchDao();
    public static final String FIND_ALL_REQUEST = "FROM Match order by id DESC";

    public void save(Match match) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            session.save(match);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    public Optional<Match> findById(Integer id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Match match = session.get(Match.class, id);
        session.getTransaction().commit();
        return Optional.ofNullable(match);
    }

    public List<Match> findAll(PageRequest pageRequest) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();

        session.beginTransaction();
        final Query<Match> query = session.createQuery(FIND_ALL_REQUEST, Match.class);
        final int firstPage = pageRequest.getPage() * pageRequest.getSize();
        query.setFirstResult(firstPage);
        final int maxPage = pageRequest.getSize();
        query.setMaxResults(maxPage);
        final List<Match> matches = query.getResultList();
        session.getTransaction().commit();

        return matches;
    }

    public int findElementsCount() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "SELECT COUNT(e) FROM Match e";
        Query<Long> query = session.createQuery(hql, Long.class);
        Long count = query.uniqueResult();
        session.getTransaction().commit();
        return count.intValue();
    }

    public List<Match> findByPlayerName(String playerName) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Match> query = session.createQuery("from Match  where first.name = :name or second.name = :name", Match.class);
        query.setParameter("name", playerName);
        List<Match> matches = query.getResultList();
        session.getTransaction().commit();
        return matches;
    }
}


