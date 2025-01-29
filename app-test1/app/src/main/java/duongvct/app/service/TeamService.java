package duongvct.app.service;

import duongvct.app.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamService {
    public Page<Team> findAllTeams(Pageable pageable, String col, String key);
    public void addTeam(Team team);
    public void updateTeam(int id, Team team);
    public void deleteTeam(int id);
    public Team findById(int id);

    public void addPlayerToTeam(int playerId, int teamId);

    public void removePlayerFromTeam(int teamId, int playerId);

    public void addCoachToTeam(int teamId, int coachId);

    public void removeCoachFromTeam(int teamId, int coachId);
}
