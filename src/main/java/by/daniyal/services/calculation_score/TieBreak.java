package by.daniyal.services.calculation_score;

import by.daniyal.entity.Player;
import lombok.ToString;

import java.util.Optional;

@ToString
public class TieBreak {
    public static final int WIN_TIE_BREAK_POINTS = 7;
    public static final int NEEDED_ADVANTAGE_POINTS = 2;

    public void addDraw(Player winner, Draw draw) {
        draw.incrementPoints(winner);
    }

    public boolean isComplete(Draw draw) {
        final int firstPlayerDraws = draw.getFirstPlayerPoints();
        final int secondPlayerDraws = draw.getSecondPlayerPoints();

        return (firstPlayerDraws >= WIN_TIE_BREAK_POINTS &&
                secondPlayerDraws < firstPlayerDraws - NEEDED_ADVANTAGE_POINTS) ||
               (secondPlayerDraws >= WIN_TIE_BREAK_POINTS &&
                firstPlayerDraws < secondPlayerDraws - NEEDED_ADVANTAGE_POINTS);
    }

    public Optional<Player> getWinner(Draw draw) {
        final int firstPlayerDraws = draw.getFirstPlayerPoints();
        final int secondPlayerDraws = draw.getSecondPlayerPoints();

        if (firstPlayerDraws >= WIN_TIE_BREAK_POINTS) {
            return Optional.ofNullable(draw.getFirst());
        } else if (secondPlayerDraws >= WIN_TIE_BREAK_POINTS) {
            return Optional.ofNullable(draw.getSecond());
        }

        return Optional.empty();
    }
}

