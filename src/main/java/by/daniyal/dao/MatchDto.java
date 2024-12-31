package by.daniyal.dao;

import by.daniyal.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@AllArgsConstructor
@Getter
public class MatchDto {
    private Integer id;
    private Player first;
    private Player second;
    private Player winner;
}
