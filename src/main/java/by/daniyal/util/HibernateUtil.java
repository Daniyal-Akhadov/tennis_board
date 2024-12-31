package by.daniyal.util;

import by.daniyal.entity.Match;
import by.daniyal.entity.Player;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static final String confFileName = "hibernate.cfg.xml";

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration()
                    .configure(confFileName)
                    .addAnnotatedClass(Player.class)
                    .addAnnotatedClass(Match.class);
            return configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new IllegalStateException("Cannot create SessionFactory", e);
        }
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
