<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Match Score</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto+Mono:wght@300&display=swap" rel="stylesheet">
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
                <a class="nav-link" href="matches.jsp">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <h1>Current match</h1>
        <div class="current-match-image"></div>
        <section class="score">
            <table class="table">
                <thead class="result">
                <tr>
                    <th class="table-text">Player</th>
                    <th class="table-text">Sets</th>
                    <th class="table-text">Games</th>
                    <th class="table-text">Points</th>
                </tr>
                </thead>
                <tbody>
                <tr class="player1">
                    <td class="table-text">${requestScope.match.first.name}</td>
                    <td class="table-text">
                        ${requestScope.score.set.firstPlayerPoints != null ? requestScope.score.set.firstPlayerPoints : 0}
                    </td>
                    <td class="table-text">
                        ${requestScope.score.game.firstPlayerPoints != null ? requestScope.score.game.firstPlayerPoints : 0}
                    </td>
                    <td class="table-text">
                        ${requestScope.score.draw.firstPlayerPoints != null ? requestScope.score.draw.firstPlayerPoints : 0}
                    </td>
                    <td class="table-text">
                        <div class="score-btn">
                            <form method="post"
                                  action="${pageContext.request.contextPath}/match-score?uuid=${param.uuid}">
                                <input type="hidden" name="player" value="${requestScope.match.first.name}">
                                <input class="form-button" type="submit" value="Score">
                            </form>
                        </div>
                    </td>
                </tr>

                <tr class="player2">
                    <td class="table-text">${requestScope.match.second.name}</td>
                    <td class="table-text">
                        ${requestScope.score.set.secondPlayerPoints != null ? requestScope.score.set.secondPlayerPoints : 0}
                    </td>
                    <td class="table-text">
                        ${requestScope.score.game.secondPlayerPoints != null ? requestScope.score.game.secondPlayerPoints : 0}
                    </td>
                    <td class="table-text">
                        ${requestScope.score.draw.secondPlayerPoints != null ? requestScope.score.draw.secondPlayerPoints : 0}
                    </td>
                    <td class="table-text">
                        <div class="score-btn">
                            <form method="post"
                                  action="${pageContext.request.contextPath}/match-score?uuid=${param.uuid}">
                                <input type="hidden" name="player" value="${requestScope.match.second.name}">
                                <input class="form-button" type="submit" value="Score">
                            </form>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </section>
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
