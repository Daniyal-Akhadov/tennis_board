package by.daniyal.servlets;

import by.daniyal.entity.Match;
import lombok.Getter;

@Getter
public class MatchUuidDto {
    private final String uuid;
    private final Match match;

    public MatchUuidDto(String uuid, Match match) {
        this.uuid = uuid;
        this.match = match;
    }
}
