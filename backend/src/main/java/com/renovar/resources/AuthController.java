package com.renovar.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renovar.dao.UserDAO;
import com.renovar.domain.User;
import com.renovar.dto.AuthResponseDTO;
import com.renovar.dto.LoginRequestDTO;
import com.renovar.dto.RegisterRequestDTO;
import com.renovar.services.JwtService;
import com.renovar.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Authentication", description = "Register and obtain JWT tokens")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserDAO userDAO;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(UserService userService, UserDAO userDAO,
                          JwtService jwtService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userDAO = userDAO;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "Register a new user",
               description = "Creates a user account and returns a JWT valid for 24 hours.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User created — JWT returned",
                content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation error — missing or blank fields",
                content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid RegisterRequestDTO dto) {
        User user = new User(null, dto.name(), dto.email(), passwordEncoder.encode(dto.password()));
        User saved = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponseDTO(jwtService.generateToken(dto.email()), saved.getId()));
    }

    @Operation(summary = "Authenticate and obtain a JWT",
               description = "Verifies credentials and returns a JWT valid for 24 hours.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Authentication successful — JWT returned",
                content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Validation error — missing or blank fields",
                content = @Content),
        @ApiResponse(responseCode = "401", description = "Invalid email or password",
                content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        User user = userDAO.findByEmail(dto.email());
        if (user == null || !passwordEncoder.matches(dto.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new AuthResponseDTO(jwtService.generateToken(dto.email()), user.getId()));
    }
}