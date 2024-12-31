package by.daniyal.services.calculation_score;

import by.daniyal.entity.Player;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Set {
    private int firstPlayerPoints;
    private int secondPlayerPoints;

    private final Player first;
    private final Player second;

    public Set(Player first, Player second) {
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

    public void incrementPointsFor(Player player) {
        if (player.equals(first)) {
            firstPlayerPoints++;
        } else if (player.equals(second)) {
            secondPlayerPoints++;
        }
    }
}

