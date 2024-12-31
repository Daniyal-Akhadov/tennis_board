package by.daniyal.dto;

import by.daniyal.entity.Player;
import lombok.Builder;

@Builder
public class MatchDto {
    private Long id;
    private Player first;
    private Player second;

}
