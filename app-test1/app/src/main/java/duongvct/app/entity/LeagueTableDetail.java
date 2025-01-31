package duongvct.app.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class LeagueTableDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "league_table_id")
    private LeagueTable leagueTable;

    @OneToMany(mappedBy = "leagueTableDetail")
    private List<TeamTableDetail> teamTableDetails = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LeagueTable getLeagueTable() {
        return leagueTable;
    }

    public void setLeagueTable(LeagueTable leagueTable) {
        this.leagueTable = leagueTable;
    }

    public List<TeamTableDetail> getTeamTableDetails() {
        return teamTableDetails;
    }

    public void setTeamTableDetails(List<TeamTableDetail> teamTableDetails) {
        this.teamTableDetails = teamTableDetails;
    }
}
