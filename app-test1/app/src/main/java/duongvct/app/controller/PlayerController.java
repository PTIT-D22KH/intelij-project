package duongvct.app.controller;

import duongvct.app.entity.Player;
import duongvct.app.repository.PlayerRepository;
import duongvct.app.service.impl.PlayerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/players")
@Tag(name = "Player Controller", description = "APIs for handling players")
public class PlayerController {

    private PlayerServiceImpl playerService;
    @Autowired
    public PlayerController(PlayerServiceImpl playerService) {
        this.playerService = playerService;
    }




    @GetMapping
    @Operation(summary = "Get all players with soring, searching and pagination", description = "This API retrieves all players with soring, searching and pagination support")
    public List<Player> showAllPlayers(
            @RequestParam(value = "col", required = false) String col,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "sortCol", required = false, defaultValue = "id") String sortCol,
            @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortCol));
        return playerService.findAllPlayers(pageable, col, key).getContent();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a player by ID", description = "This API retrieves a player by its ID")
    public Player showPlayer(@PathVariable int id) {
        return playerService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create a player", description = "This API creates a new player")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPlayer(@RequestBody Player player) {
        playerService.addPlayer(player);

    }



    @PutMapping("/{id}")
    @Operation(summary = "Update a player by ID", description = "This API updates a player by its ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePlayer(@PathVariable int id, @RequestBody Player player) {
        playerService.updatePlayer(id, player);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a player by ID", description = "This API deletes a player by its ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlayer(@PathVariable int id) {
        playerService.deletePlayer(id);
    }
}
