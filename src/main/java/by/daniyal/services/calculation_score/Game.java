package by.daniyal.services.calculation_score;

import lombok.Getter;
import lombok.ToString;
import by.daniyal.entity.Player;

@Getter
@ToString
public class Game {
    private int firstPlayerPoints;
    private int secondPlayerPoints;

    private final Player first;
    private final Player second;

    public Game(Player first, Player second) {
        this.first = first;
        this.second = second;
    }

    public void resetAll() {
        firstPlayerPoints = 0;
        secondPlayerPoints = 0;
    }

    public void resetFirstPlayer() {
        firstPlayerPoints = 0;
    }

    public void resetSecondPlayer() {
        secondPlayerPoints = 0;
    }

    public int getPoints(Player player) {
        return player == first ? firstPlayerPoints : secondPlayerPoints;
    }

    public void resetPlayer(Player player) {
        if (player == first) {
            firstPlayerPoints = 0;
        } else if (player == second) {
            secondPlayerPoints = 0;
        }
    }

    public void incrementPoints(Player winningPlayer) {
        if (winningPlayer.equals(first)) {
            firstPlayerPoints++;
        } else if (winningPlayer.equals(second)) {
            secondPlayerPoints++;
        }
    }
}
