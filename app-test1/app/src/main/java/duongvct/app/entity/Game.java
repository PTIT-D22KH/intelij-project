package duongvct.app.entity;

import jakarta.persistence.*;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="home_team_id")
    private Team home;

    @ManyToOne
    @JoinColumn(name="away_team_id")
    private Team away;

    @ManyToOne
    @JoinColumn(name="league_round_id")
    private LeagueRound leagueRound;
}
