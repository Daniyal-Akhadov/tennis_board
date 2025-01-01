package by.daniyal.servlets;

import by.daniyal.entity.Match;
import by.daniyal.services.calculation_score.Score;
import lombok.Getter;

@Getter
public class MatchScoreDto {
    private final Score score;
    private final Match match;

    public MatchScoreDto(Score score, Match match) {
        this.score = score;
        this.match = match;
    }
}
