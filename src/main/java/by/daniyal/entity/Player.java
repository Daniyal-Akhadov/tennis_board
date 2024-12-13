package by.daniyal.entity;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
@ToString
public class Player {
    private Integer id;
    private String name;

    public Player(String name) {
        this.name = name;
    }
}
