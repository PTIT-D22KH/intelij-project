package duongvct.app.controller;

import duongvct.app.dto.LeagueDTO;
import duongvct.app.entity.League;
import duongvct.app.service.impl.LeagueServiceImpl;
import duongvct.app.utls.CustomResponse;
import duongvct.app.utls.PaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leagues")
@Tag(name = "League Controller", description = "APIs for handling leagues")
public class LeagueController {
    private LeagueServiceImpl leagueService;

    @Autowired
    public LeagueController(LeagueServiceImpl leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping
    @Operation(summary = "Show all leagues with pagination", description = "This API shows all leagues with pagination")
    public PaginatedResponse<LeagueDTO> showLeaguesWithPagination(@RequestParam(required = false) Integer current,
                                                               @RequestParam(required = false) Integer pageSize) {
        if (current != null && pageSize != null) {
            Pageable pageable = PageRequest.of(current - 1, pageSize); // Decrement current by 1 for Pageable
            Page<League> leaguePage = leagueService.findAll(pageable);
            return new PaginatedResponse<>(
                    LeagueDTO.convert(leaguePage.getContent()),
                    leaguePage.getNumber() + 1,
                    leaguePage.getSize(),
                    leaguePage.getTotalPages(),
                    leaguePage.getTotalElements()
            );
        } else {
            List<League> leagues = leagueService.findAll();
            return new PaginatedResponse<>(
                    LeagueDTO.convert(leagues),
                    1,
                    leagues.size(),
                    1,
                    leagues.size()
            );
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get league by id", description = "This API gets a league by its id")
    public LeagueDTO getLeagueById(@PathVariable int id) {
        return new LeagueDTO(leagueService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update league by id", description = "This API updates a league by its id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CustomResponse<LeagueDTO> updateLeagueById(@PathVariable int id, @RequestBody LeagueDTO leagueDTO) {
        League league = leagueService.findById(id);
        league.setName(leagueDTO.getName());
        league.setNumRounds(leagueDTO.getNumRounds());
        leagueService.updateLeague(id, league);
        return new CustomResponse<>("Updated league with id = " + id, new LeagueDTO(league), 204);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLeague(@PathVariable int id) {
        leagueService.deleteLeague(id);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomResponse<LeagueDTO> createLeague(@RequestBody LeagueDTO leagueDTO) {
        League league = new League(leagueDTO.getId(), leagueDTO.getName(), leagueDTO.getNumRounds());
        leagueService.createLeague(league);
        return new CustomResponse<>("Created new league.", new LeagueDTO(league), 201);
    }
}
