package by.daniyal.services;

import by.daniyal.entity.Player;
import by.daniyal.services.calculation_score.*;
import by.daniyal.util.Logger;
import lombok.Getter;

import java.util.Optional;

import static java.lang.Math.abs;

public class MatchScoreCalculationService {

    public static final int VALUE_TO_REACHED_SET_WITHOUT_ADVANTAGE = 7;
    public static final int POINTS_TO_WIN_SET = 2;
    public static final int POINTS_FOR_STARTING_TIE_BREAK = 6;

    private final Player first;
    private final Player second;

    private final Set set;
    private final Game game;
    private final Draw draw;

    private final TieBreak tieBreak;

    @Getter
    private boolean isTieBreak;
    @Getter
    private boolean isEnd;

    public MatchScoreCalculationService(Player first, Player second) {
        this.first = first;
        this.second = second;
        this.set = new Set(first, second);
        this.game = new Game(first, second);
        this.draw = new Draw(first, second);
        this.tieBreak = new TieBreak();
    }

    public Optional<Score> calculate(Player winner) {
        if (isEnd) {
            return Optional.empty();
        }

        Player winnerPlayer = determineWinner(winner);

        if (game.getFirstPlayerPoints() == POINTS_FOR_STARTING_TIE_BREAK
            && game.getSecondPlayerPoints() == POINTS_FOR_STARTING_TIE_BREAK) {
            startTieBreak();
        }

        if (isTieBreak) {
            handleTieBreak(winnerPlayer);
        } else {
            handleDrawPoints(winnerPlayer);
        }

        handleSet();

        Optional<Player> setWinner = findSetWinner();
        setWinner.ifPresent(this::announceWinner);

        Score result = new Score(draw, game, set);
        return Optional.of(result);
    }

    public Score getScore() {
        return new Score(draw, game, set);
    }

    private void handleSet() {
        if (game.getPoints(first) == VALUE_TO_REACHED_SET_WITHOUT_ADVANTAGE) {
            increaseSet(first);
        } else if (game.getPoints(second) == VALUE_TO_REACHED_SET_WITHOUT_ADVANTAGE) {
            increaseSet(second);
        }
    }

    private Player determineWinner(Player winner) {
        return winner.equals(first) ? first : second;
    }


    private void increaseSet(Player player) {
        game.resetPlayer(player);
        set.incrementPointsFor(player);
    }

    private Optional<Player> findSetWinner() {
        Player winner = null;

        if (set.getFirstPlayerPoints() == POINTS_TO_WIN_SET) {
            winner = first;
        } else if (set.getSecondPlayerPoints() == POINTS_TO_WIN_SET) {
            winner = second;
        }

        return Optional.ofNullable(winner);
    }

    private void announceWinner(Player player) {
        Logger.log(player + " wins!");
        isEnd = true;
    }

    private void handleTieBreak(Player winner) {
        if (isTieBreak) {
            tieBreak.addDraw(winner, draw);

            if (tieBreak.isComplete(draw) != true) {
                return;
            }

            tieBreakWon(winner);
        }
    }

    private void startTieBreak() {
        isTieBreak = true;
    }

    private void tieBreakWon(Player winner) {
        tieBreak.getWinner(draw).ifPresent(this::resetGameForWinner);
        set.incrementPointsFor(winner);
        game.resetAll();
        draw.resetAll();
        isTieBreak = false;
    }

    private void resetGameForWinner(Player player) {
        if (player.equals(first)) {
            game.resetFirstPlayer();
        } else if (player.equals(second)) {
            game.resetSecondPlayer();
        }
    }

    private void handleDrawPoints(Player winningPlayer) {
        draw.incrementPoints(winningPlayer);

        if (isGameWon(draw.getFirstPlayerPoints(), draw.getSecondPlayerPoints())) {
            game.incrementPoints(winningPlayer);
            draw.resetAll();
        }
    }

    private boolean isGameWon(int player1Points, int player2Points) {
        return player1Points >= 4 && abs(player1Points - player2Points) >= 2 ||
               player2Points >= 4 && abs(player2Points - player1Points) >= 2;
    }
}
