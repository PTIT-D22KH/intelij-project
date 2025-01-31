package duongvct.app.entity;

import jakarta.persistence.*;

@Entity
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private int numRounds;

    @OneToOne(mappedBy = "league")
    private LeagueTable leagueTable;

    public League(int id, String name, int numRounds) {
        this.id = id;
        this.name = name;
        this.numRounds = numRounds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumRounds() {
        return numRounds;
    }

    public void setNumRounds(int numRounds) {
        this.numRounds = numRounds;
    }

    public LeagueTable getLeagueTable() {
        return leagueTable;
    }

    public void setLeagueTable(LeagueTable leagueTable) {
        this.leagueTable = leagueTable;
    }
}
