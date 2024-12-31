package by.daniyal.services.calculation_score;

import by.daniyal.entity.Player;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Draw {
    private int firstPlayerPoints;
    private int secondPlayerPoints;

    private final Player first;
    private final Player second;

    public Draw(Player first, Player second) {
        this.first = first;
        this.second = second;
    }

    public void resetAll() {
        firstPlayerPoints = 0;
        secondPlayerPoints = 0;
    }

    public void incrementPoints(Player winningPlayer) {
        if (winningPlayer == first) {
            firstPlayerPoints++;
        } else if (winningPlayer == second) {
            secondPlayerPoints++;
        }
    }

    public void resetFor(Player winningPlayer) {
        if (winningPlayer == first) {
            firstPlayerPoints = 0;
        } else if (winningPlayer == second) {
            secondPlayerPoints = 0;
        }
    }
}
