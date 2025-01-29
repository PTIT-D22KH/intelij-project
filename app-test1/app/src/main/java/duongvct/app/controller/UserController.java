package duongvct.app.controller;

import duongvct.app.entity.User;
import duongvct.app.service.impl.UserServiceImpl;
import duongvct.app.utls.PaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Controller", description = "APIs for handling users")
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Show all users", description = "This API shows all users")
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public PaginatedResponse<User> showAllUsers(@RequestParam(required = false) Integer current,
                                                @RequestParam(required = false) Integer pageSize) {
        if (current != null && pageSize != null) {
            Pageable pageable = PageRequest.of(current - 1, pageSize); // Decrement current by 1 for Pageable
            Page<User> userPage = userService.findAll(pageable);
            return new PaginatedResponse<>(
                    userPage.getContent(),
                    userPage.getNumber(),
                    userPage.getSize(),
                    userPage.getTotalPages(),
                    userPage.getTotalElements()
            );
        } else {
            List<User> users = userService.findAll();
            return new PaginatedResponse<>(
                    users,
                    1,
                    users.size(),
                    1,
                    users.size()
            );
        }
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "This API creates a new user")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewUser(@RequestBody User user) {
        userService.registerUser(user);
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "This API registers a new user")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody User user) {
        userService.registerUser(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "This API updates a user")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable int id, @RequestBody User user) {
        userService.updateUser(id, user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by id", description = "This API gets a user by id")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user by id", description = "This API deletes a user by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}

