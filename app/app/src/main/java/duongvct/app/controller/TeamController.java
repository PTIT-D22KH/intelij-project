package duongvct.app.controller;

import duongvct.app.entity.Team;
import duongvct.app.service.impl.TeamServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
@Tag(name = "Team Controller", description = "APIs for handling teams")
public class TeamController {
    private TeamServiceImpl teamService;

    @Autowired
    public TeamController(TeamServiceImpl teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    @Operation(summary = "Create a team", description = "This API creates a new team")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTeam(@RequestBody Team team) {
        teamService.addTeam(team);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a team by ID", description = "This API updates a team by its ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTeam(@RequestBody Team team, @PathVariable int id) {
        teamService.updateTeam(id, team);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a team by ID", description = "This API deletes a team by its ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeam(@PathVariable int id) {
        teamService.deleteTeam(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a team by ID", description = "This API retrieves a team by its ID")
    public Team showTeam(@PathVariable int id) {
        return teamService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Get all teams with soring, searching and pagination", description = "This API retrieves all players with sorting, searching and pagination support")
    public List<Team>  showAllTeams(
            @RequestParam(value = "col", required = false) String col,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "sortCol", required = false, defaultValue = "id") String sortCol,
            @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortCol));
        return teamService.findAllTeams(pageable, col, key).getContent();
    }

    @PostMapping("/{teamId}/player/{playerId}")
    @Operation(summary = "Add a player to a team", description = "This API adds a player to a team")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addPlayerToTeam(@PathVariable int teamId, @PathVariable int playerId) {
        teamService.addPlayerToTeam(playerId, teamId);
    }

    @DeleteMapping("/{teamId}/player/{playerId}")
    @Operation(summary = "Remove a player from a team", description = "This API removes a player from a team")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePlayerFromTeam(@PathVariable int teamId, @PathVariable int playerId) {
        teamService.removePlayerFromTeam(teamId, playerId);
    }

    @PostMapping("/{teamId}/coach/{coachId}")
    @Operation(summary = "Assign a coach to a team", description = "This API assigns a coach to a team")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addCoachToTeam(@PathVariable int teamId, @PathVariable int coachId) {
        teamService.addCoachToTeam(teamId, coachId);
    }

    @DeleteMapping("/{teamId}/coach/{coachId}")
    @Operation(summary = "Remove a player from a team", description = "This API removes a player from a team")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCoachFromTeam(@PathVariable int teamId, @PathVariable int coachId) {
        teamService.removeCoachFromTeam(teamId, coachId);
    }

}
