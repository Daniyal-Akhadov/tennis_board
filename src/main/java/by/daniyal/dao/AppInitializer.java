package by.daniyal.dao;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppInitializer implements ServletContextListener {
    private final PlayerDao playerDao = PlayerDao.INSTANCE;
    private final MatchDao matchDao = MatchDao.INSTANCE;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        savePlayers();

        MatchDefaultInitializer matchInitializer = MatchDefaultInitializer.getInstance();
        matchInitializer.initialize();

        for (int i = 0; i < matchInitializer.size(); i++) {
            matchDao.save(matchInitializer.getMatch(i));
        }
    }

    private void savePlayers() {
        PlayerDefaultInitializer playerInitializer = PlayerDefaultInitializer.getInstance();
        playerInitializer.initialize();

        for (int i = 0; i < playerInitializer.size(); i++) {
            playerDao.save(playerInitializer.getPlayer(i));
        }
    }
}
