package duongvct.app.controller;

import duongvct.app.entity.Coach;
import duongvct.app.service.impl.CoachServiceImpl;
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
@RequestMapping("/api/v1/coaches")
@Tag(name = "Coach Controller", description = "APIs for handling coaches")
public class CoachController {
    private CoachServiceImpl coachService;

    @Autowired
    public CoachController(CoachServiceImpl coachService) {
        this.coachService = coachService;
    }

    @GetMapping
    @Operation(summary = "Get all coaches with soring, searching and pagination", description = "This API retrieves all coaches with soring, searching and pagination support")
    public List<Coach> showAllCoaches(
        @RequestParam(value = "col", required = false) String col,
        @RequestParam(value = "key", required = false) String key,
        @RequestParam(value = "sortCol", required = false, defaultValue = "id") String sortCol,
        @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir,
        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
        @RequestParam(value = "size", required = false, defaultValue = "5") int size
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortCol));
        return coachService.findAllCoaches(pageable, col, key).getContent();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a coach by ID", description = "This API retrieves a coach by its ID")
    public Coach showCoach(@PathVariable int id) {
        return coachService.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a coach by ID", description = "This API updates a coach by its ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCoach(@PathVariable int id, @RequestBody Coach coach) {
        coachService.updateCoach(id, coach);
    }

    @PostMapping
    @Operation(summary = "Create a coach", description = "This API creates a new coach")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCoach(@RequestBody Coach coach) {
        coachService.addCoach(coach);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a coach by ID", description = "This API deletes a coach by its ID")
    public void deleteCoach(@PathVariable int id) {
        coachService.deleteCoach(id);
    }


}
