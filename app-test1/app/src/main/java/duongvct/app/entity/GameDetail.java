package duongvct.app.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class GameDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int homeGoals;

    @Column
    private int awayGoals;

    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy = "gameDetail")
    private List<GoalDetail> goalDetails = new ArrayList<>();
}
