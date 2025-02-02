package duongvct.app.entity;

import jakarta.persistence.*;

@Entity
public class TeamTableDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int points;

    @Column
    private int goalsFor;

    @Column
    private int goalsAgainst;

//    @Column
//    private int goalDifference;

    @Column
    private int wins;

    @Column
    private int draws;

    @Column
    private int losses;

    @Column
    private int played;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name="league_table_id")
    private LeagueTable leagueTable;



}
