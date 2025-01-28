package duongvct.app.controller;

import duongvct.app.dto.LoginRequest;
import duongvct.app.dto.LoginResponse;
import duongvct.app.entity.User;
import duongvct.app.security.CustomUserDetails;
import duongvct.app.utls.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication API", description = "API for user authentication")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    @Operation(summary = "Authenticate user and generate JWT token", description = "This API authenticates the user and generates a JWT token")
    public LoginResponse createAuthenticationToken(@RequestBody LoginRequest loginRequest) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        final CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails.getUsername());

        // Get the User entity from CustomUserDetails
        User user = userDetails.getUser();

        return new LoginResponse(token, user);
    }

    @GetMapping("/account")
    @Operation(summary = "Get authenticated user data", description = "This API returns the data of the authenticated user")
    public ResponseEntity<?> getAccount(HttpServletRequest request) {
        CustomUserDetails userDetails = jwtTokenUtil.getUserDetailsFromToken(request);
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Invalid or expired JWT token");
        }
        User user = userDetails.getUser();
        return ResponseEntity.ok(Collections.singletonMap("data", Collections.singletonMap("user", user)));
    }
}