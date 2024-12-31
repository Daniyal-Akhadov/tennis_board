package by.daniyal.servlets;

import by.daniyal.dao.PlayerDao;
import by.daniyal.entity.Match;
import by.daniyal.entity.Player;
import by.daniyal.services.OngoingMatchesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "new-match", urlPatterns = "/new-match")
public class NewMatchServlet extends HttpServlet {

    public static final String NEW_MATCH_JSP = "new-match.jsp";
    public static final String MATCH_SCORE_BY_UUID_JSP = "/match-score?uuid=%s";

    private static final PlayerDao playerDao = PlayerDao.INSTANCE;
    private static final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request
                .getRequestDispatcher(NEW_MATCH_JSP)
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstPlayerName = request.getParameter("playerOne");
        String secondPlayerName = request.getParameter("playerTwo");

        Player firstPlayer = playerDao.findByName(firstPlayerName)
                .orElseGet(() -> playerDao.save(new Player(firstPlayerName)));

        Player secondPlayer = playerDao.findByName(secondPlayerName)
                .orElseGet(() -> playerDao.save(new Player(secondPlayerName)));

        Match match = new Match(1L, firstPlayer, secondPlayer, null);
        UUID uuid = UUID.randomUUID();
        ongoingMatchesService.save(uuid, match);

        response.sendRedirect(formatedMatchScoreJspRequest(uuid));
    }

    private static String formatedMatchScoreJspRequest(UUID uuid) {
        return MATCH_SCORE_BY_UUID_JSP.formatted(uuid);
    }
}
