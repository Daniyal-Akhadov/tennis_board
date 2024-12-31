package by.daniyal.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@ToString
@Table(name = "match")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "player1", nullable = false)
    private Player first;

    @ManyToOne
    @JoinColumn(name = "player2", nullable = false)
    private Player second;

    @ManyToOne()
    @JoinColumn(name = "winner")
    private Player winner;

    public Match(Player first, Player second, Player winner) {
        this.first = first;
        this.second = second;
        this.winner = winner;
    }

    public Match(Long id, Player first, Player second, Player winner) {
        this.id = id;
        this.first = first;
        this.second = second;
        this.winner = winner;
    }
}
