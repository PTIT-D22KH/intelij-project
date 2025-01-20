package duongvct.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication API", description = "API for user authentication")
public class AuthController {
    @GetMapping("/hello")
    @Operation(summary = "Hello World", description = "This is a simple hello world API")
    public String hello() {
        return "Hello World";
    }



}