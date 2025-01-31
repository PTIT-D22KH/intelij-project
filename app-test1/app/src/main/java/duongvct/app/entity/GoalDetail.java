package duongvct.app.entity;

import jakarta.persistence.*;

@Entity
public class GoalDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int minute;

    @Column
    private boolean isPenalty;

    @Column
    private boolean isOwnGoal;

    @OneToOne
    @JoinColumn(name="player_id")
    private Player player;

    @OneToOne
    @JoinColumn(name="team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name="game_detail_id")
    private GameDetail gameDetail;

}
