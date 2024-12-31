package by.daniyal.servlets;

import by.daniyal.dao.MatchDao;
import by.daniyal.entity.Match;
import by.daniyal.entity.Player;
import by.daniyal.services.MatchScoreCalculationService;
import by.daniyal.services.OngoingMatchesService;
import by.daniyal.services.calculation_score.Score;
import by.daniyal.util.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@WebServlet(name = "match-score", urlPatterns = "/match-score")
public class MatchScoreServlet extends HttpServlet {

    private static final String MATCH_SCORE_JSP = "match-score.jsp";
    private static final String MATCHES_SERVLET = "/matches";

    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.INSTANCE;
    private final MatchDao matchDao = MatchDao.INSTANCE;

    private MatchScoreCalculationService matchScoreCalculationService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        Optional<Match> matchOptional = ongoingMatchesService.find(UUID.fromString(uuid));
        Match match = matchOptional.orElseThrow(NoSuchElementException::new);
        request.setAttribute("match", match);
        request.setAttribute("uuid", uuid);
        matchScoreCalculationService = new MatchScoreCalculationService(match.getFirst(), match.getSecond());
        request.getRequestDispatcher(MATCH_SCORE_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String player = request.getParameter("player");

        Optional<Match> matchOptional = ongoingMatchesService.find(UUID.fromString(uuid));
        Match match = matchOptional.orElseThrow(NoSuchElementException::new);

        Logger.log("Match found: " + match);
        Logger.log("Player found: " + player);
        Player winner = defineWinner(match, player);
        match.setWinner(winner);

        Optional<Score> score = matchScoreCalculationService.calculate(winner);

        if (matchScoreCalculationService.isEnd()) {
            handleEnd(request, response, match);
        } else {
            handleScore(request,
                    response,
                    score.orElseThrow(() -> new NoSuchElementException("Match score not found")),
                    match);
        }
    }

    @SneakyThrows
    private void handleEnd(HttpServletRequest request, HttpServletResponse response, Match match) {
        ongoingMatchesService.remove(match);
        matchDao.save(match);
        request.getSession().setAttribute("winner", match.getWinner());
        response.sendRedirect(MATCHES_SERVLET);
    }

    @SneakyThrows
    private void handleScore(HttpServletRequest request, HttpServletResponse response, Score score, Match match) {
        request.setAttribute("score", score);
        request.setAttribute("match", match);
        request.getRequestDispatcher(MATCH_SCORE_JSP).forward(request, response);
    }

    private static Player defineWinner(Match match, String player) {
        Player first = match.getFirst();
        Player second = match.getSecond();
        Player winner = null;

        if (first.getName().equalsIgnoreCase(player)) {
            winner = first;
        } else if (second.getName().equalsIgnoreCase(player)) {
            winner = second;
        }
        return winner;
    }
}

