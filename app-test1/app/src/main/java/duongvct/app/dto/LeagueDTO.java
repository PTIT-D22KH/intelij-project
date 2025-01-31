package duongvct.app.dto;

import duongvct.app.entity.League;

import java.util.List;
import java.util.stream.Collectors;

public class LeagueDTO {
    private int id;
    private String name;
    private int numRounds;

    public LeagueDTO(int id, String name, int numRounds) {
        this.id = id;
        this.name = name;
        this.numRounds = numRounds;
    }

    public LeagueDTO(League league) {
        this.id = league.getId();
        this.name = league.getName();
        this.numRounds = league.getNumRounds();
    }

    public static List<LeagueDTO> convert(List<League> content) {
        return content.stream()
                .map(LeagueDTO::new)
                .collect(Collectors.toList());
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

}
