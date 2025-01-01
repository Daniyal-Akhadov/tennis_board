package by.daniyal.dao;

import by.daniyal.entity.Player;
import by.daniyal.services.MatchScoreCalculationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class GameTest {

    private final Player first = new Player("first");
    private final Player second = new Player("second");

    @Test
    public void game_0_40_Win() {
        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService(first, second);

        simulatePointsScoring(first, 5, matchScoreCalculationService);
        simulatePointsScoring(second, 0, matchScoreCalculationService);

        Assertions.assertThat(matchScoreCalculationService.getScore().getGame().getFirstPlayerPoints())
                .isEqualTo(1);
    }

    @Test
    public void game40_40NotFinished() {
        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService(first, second);

        simulatePointsScoring(first, 3, matchScoreCalculationService);
        simulatePointsScoring(second, 3, matchScoreCalculationService);

        matchScoreCalculationService.calculate(first);

        Assertions.assertThat(matchScoreCalculationService.getScore().getGame().getFirstPlayerPoints())
                .isEqualTo(0);
    }

    @Test
    public void game60_60NotFinished() {
        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService(first, second);

        simulatePointsScoring(first, 23, matchScoreCalculationService);
        simulatePointsScoring(second, 23, matchScoreCalculationService);

        for (int i = 0; i < 2; i++) {
            simulatePointsScoring(first, 1, matchScoreCalculationService);
            simulatePointsScoring(second, 1, matchScoreCalculationService);
        }
        for (int i = 0; i < 5; i++) {
            simulatePointsScoring(first, 1, matchScoreCalculationService);
        }


        System.out.println(matchScoreCalculationService.getScore());
        System.out.println(matchScoreCalculationService.isTieBreak());

        Assertions.assertThat(matchScoreCalculationService.isTieBreak())
                .isEqualTo(true);
    }

    private void simulatePointsScoring(Player player, int points, MatchScoreCalculationService matchScoreCalculationService) {
        for (int i = 0; i < points; i++) {
            matchScoreCalculationService.calculate(player);
        }
    }


}
