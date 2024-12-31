package by.daniyal.services.calculation_score;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Score {
    private final Draw draw;
    private final Game game;
    private final Set set;
}

