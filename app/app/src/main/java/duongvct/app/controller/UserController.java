package duongvct.app.controller;

import duongvct.app.entity.User;
import duongvct.app.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<User> showAllUsers() {
        return userService.findAll();
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "This API creates a new user")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody User user) {
        userService.registerUser(user);
    }
}