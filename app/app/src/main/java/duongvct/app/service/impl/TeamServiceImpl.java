package duongvct.app.service.impl;

import duongvct.app.entity.Coach;
import duongvct.app.entity.Player;
import duongvct.app.entity.Team;
import duongvct.app.exception.PlayerAlreadyHasTeamException;
import duongvct.app.exception.ResourceNotContainException;
import duongvct.app.exception.ResourceNotFoundException;
import duongvct.app.repository.CoachRepository;
import duongvct.app.repository.PlayerRepository;
import duongvct.app.repository.TeamRepository;
import duongvct.app.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private PlayerRepository playerRepository;
    private CoachRepository coachRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, PlayerRepository playerRepository, CoachRepository coachRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.coachRepository = coachRepository;
    }

    @Override
    public Page<Team> findAllTeams(Pageable pageable, String col, String key) {
        if (key != null && col != null && !key.isBlank()) {
            switch (col) {
                case "name":
                    return teamRepository.findByNameContainingIgnoreCase(key, pageable);
                case "country":
                    return teamRepository.findByCountryContainingIgnoreCase(key, pageable);
                case "ranked":
                    try {
                        int ranked = Integer.parseInt(key);
                        return teamRepository.findByRanked(ranked, pageable);
                    } catch (Exception exception) {
                        return teamRepository.findAll(pageable);
                    }
                default:
                    return teamRepository.findAll(pageable);

            }
        }
        return teamRepository.findAll(pageable);
    }


    @Override
    public void addTeam(Team team) {
        teamRepository.save(team);
    }

    @Override
    public void updateTeam(int id, Team team) {
        Team currentTeam = teamRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Team with id " + id + " not found"));
//        currentTeam.setCoach(team.getCoach());
        currentTeam.setCountry(team.getCountry());
        currentTeam.setName(team.getName());
        currentTeam.setRanked(team.getRanked());
//        currentTeam.setPlayers(team.getPlayers());
        teamRepository.save(currentTeam);
    }

    @Override
    public void deleteTeam(int id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Team with id " + id + " not found"));


        for (Player player : team.getPlayers()) {
            Player curr = playerRepository.findById(player.getId()).orElse(null);
            if (curr.getTeam().getId() == id) {
                curr.setTeam(null);
                playerRepository.save(curr);
            }
        }
        team.setPlayers(new ArrayList<>());
        removeCoachFromTeam(id, team.getCoach().getId());
        teamRepository.deleteById(id);
    }

    @Override
    public Team findById(int id) {
        return teamRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Team with id " + id + " not found"));
    }

    @Override
    public void addPlayerToTeam(int playerId, int teamId) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException("Player with id " + playerId + " not found"));
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new ResourceNotFoundException("Team with id " + teamId + " not found")
        );
        if (player.getTeam() != null) {
            throw new PlayerAlreadyHasTeamException("Player with id " + playerId + " already has a club");
        }
        player.setTeam(team);
        team.getPlayers().add(player);
        team.setPlayers(team.getPlayers());
        playerRepository.save(player);
        teamRepository.save(team);
    }

    @Override
    public void removePlayerFromTeam(int teamId, int playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException("Player with id " + playerId + " not found"));
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new ResourceNotFoundException("Team with id " + teamId + " not found")
        );
        if (player.getTeam() == null || player.getTeam().getId() != teamId) {
            throw new ResourceNotContainException("Player with id " + playerId + " is not assigned to team with id " + teamId);
        }
        player.setTeam(null);
        List<Player> playerList = team.getPlayers();
        playerList.removeIf(player1 -> player1.getId() == player.getId());
        team.setPlayers(playerList);
        playerRepository.save(player);
        teamRepository.save(team);
    }

    @Override
    public void addCoachToTeam(int teamId, int coachId) {
        Coach coach = coachRepository.findById(coachId).orElseThrow(() -> new ResourceNotFoundException("Coach with id " + coachId + " not found"));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("Team with id " + teamId + " not found"));
        if(coach.getTeam() != null) {
            Team currTeam = teamRepository.findById(coach.getTeam().getId()).orElse(null);
            coach.setTeam(null);
            currTeam.setCoach(null);
            teamRepository.save(currTeam);
            coachRepository.save(coach);
        }
        if (team.getCoach() != null) {
            Coach currCoach = coachRepository.findById(team.getCoach().getId()).orElse(null);
            team.setCoach(null);
            currCoach.setTeam(null);
            teamRepository.save(team);
            coachRepository.save(currCoach);
        }
        coach.setTeam(team);
        team.setCoach(coach);
        teamRepository.save(team);
        coachRepository.save(coach);
    }

    @Override
    public void removeCoachFromTeam(int teamId, int coachId) {
        Coach coach = coachRepository.findById(coachId).orElseThrow(() -> new ResourceNotFoundException("Coach with id " + coachId + " not found"));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("Team with id " + teamId + " not found"));
        if (coach.getTeam() == null || coach.getTeam().getId() != team.getId()) {
            throw new ResourceNotContainException("Coach with id " + coachId + " is not assigned to team with id " + teamId);
        }
        coach.setTeam(null);
        team.setCoach(null);
        coachRepository.save(coach);
        teamRepository.save(team);

    }

}
