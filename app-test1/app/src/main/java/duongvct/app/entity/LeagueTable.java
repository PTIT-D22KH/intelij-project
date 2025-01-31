package duongvct.app.entity;

import jakarta.persistence.*;

@Entity
public class LeagueTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "league_id")
    private League league;

    @OneToOne(mappedBy = "leagueTable")
    private LeagueTableDetail leagueTableDetail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public LeagueTableDetail getLeagueTableDetail() {
        return leagueTableDetail;
    }

    public void setLeagueTableDetail(LeagueTableDetail leagueTableDetail) {
        this.leagueTableDetail = leagueTableDetail;
    }
}
