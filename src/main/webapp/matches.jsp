<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Finished Matches</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <script src="js/app.js"></script>
</head>

<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href="index.html">Home</a>
                <a class="nav-link" href="matches">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <h1>Matches</h1>
        <div class="input-container">
            <form action="/matches" method="get">
                <input name="filter_by_player_name" class="input-filter" placeholder="Filter by name" type="text"
                       required/>
            </form>

            <div>
                <a href="/matches">
                    <button class="btn-filter">Reset Filter</button>
                </a>
            </div>
        </div>

        <table class="table-matches">
            <tr>
                <th>Player One</th>
                <th>Player Two</th>
                <th>Winner</th>
            </tr>
            <c:forEach var="match" items="${requestScope.matches}">
                <tr>
                    <td>${match.first.name}</td>
                    <td>${match.second.name}</td>
                    <td><span class="winner-name-td">${match.winner.name}</span></td>
                </tr>
            </c:forEach>
        </table>

        <div class="pagination">
            <c:if test="${requestScope.pageRequest.hasPrevious()}">
                <a class="prev"
                   href="http://localhost:8080/matches?page=${requestScope.pageRequest.previousPage}">&lt;</a>
            </c:if>

            <c:set var="currentPage" value="${requestScope.pageRequest.page}"/>
            <c:set var="totalPages" value="${requestScope.pageRequest.totalPages}"/>

            <c:set var="startPage" value="${currentPage - 1}"/>
            <c:set var="endPage" value="${currentPage + 1}"/>

            <c:if test="${startPage < 0}">
                <c:set var="startPage" value="0"/>
            </c:if>

            <c:if test="${endPage >= totalPages}">
                <c:set var="endPage" value="${totalPages - 1}"/>
            </c:if>

            <c:forEach var="i" begin="${startPage}" end="${endPage}">
                <c:if test="${i < totalPages}">
                    <a class="num-page ${i == currentPage ? 'current' : ''}"
                       href="http://localhost:8080/matches?page=${i}">${i + 1}</a>
                </c:if>
            </c:forEach>

            <c:if test="${requestScope.pageRequest.hasNext()}">
                <a class="next" href="http://localhost:8080/matches?page=${requestScope.pageRequest.page + 1}">&gt;</a>
            </c:if>
        </div>
    </div>
</main>

<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>
            roadmap.</p>
    </div>
</footer>
</body>
</html>
