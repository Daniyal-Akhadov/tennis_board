package by.daniyal.servlets;

import by.daniyal.dao.MatchDao;
import by.daniyal.entity.Match;
import by.daniyal.entity.PageRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "matches", urlPatterns = "/matches")
public class MatchesServlet extends HttpServlet {

    private final MatchDao matchDao = MatchDao.INSTANCE;

    private static final int DEFAULT_PAGE_ELEMENTS_COUNT = 5;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = parsePage(request);
        String filterByPlayerName = parsFilterByPlayerName(request);

        List<Match> matches;
        PageRequest pageRequest;

        if (filterByPlayerName != null && filterByPlayerName.isEmpty() != true) {
            matches = matchDao.findByPlayerName(filterByPlayerName);
            pageRequest = PageRequest.of(page, DEFAULT_PAGE_ELEMENTS_COUNT, matches.size());
        } else {
            int elementsCount = matchDao.findElementsCount();
            pageRequest = PageRequest.of(page, DEFAULT_PAGE_ELEMENTS_COUNT, elementsCount);
            matches = matchDao.findAll(pageRequest);
        }

        request.setAttribute("lookingForPlayer", filterByPlayerName);
        request.setAttribute("pageRequest", pageRequest);
        request.setAttribute("matches", matches);
        request.setAttribute("winner", request.getSession().getAttribute("winner"));
        request.getRequestDispatcher("matches.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private static String parsFilterByPlayerName(HttpServletRequest request) {
        return request.getParameter("filter_by_player_name");
    }

    private static Integer parsePage(HttpServletRequest request) {
        String page = request.getParameter("page");
        return page == null || page.isEmpty() ? 0 : Integer.parseInt(page);
    }
}
