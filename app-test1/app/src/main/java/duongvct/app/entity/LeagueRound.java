package duongvct.app.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class LeagueRound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;


    @OneToMany(mappedBy = "leagueRound")
    private List<Game> games = new ArrayList<>();
}
