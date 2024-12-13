package by.daniyal.entity;

import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Builder
public class Match {
    private Integer id;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final Player winner;

    public Match(Player firstPlayer, Player secondPlayer, Player winner) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.winner = winner;
    }
}
