package com.renovar.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.renovar.domain.User;
import com.renovar.dto.UserDTO;
import com.renovar.mapper.UserMapper;
import com.renovar.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Users", description = "Users who own and manage IoT devices")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserMapper mapper;

    @Operation(summary = "Get user by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(
            @Parameter(description = "User ID") @PathVariable Integer id) {
        return ResponseEntity.ok(mapper.toDTO(service.findById(id)));
    }

    @Operation(summary = "Get all users")
    @ApiResponse(responseCode = "200", description = "List of all registered users")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> dtos = service.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Register a new user")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User created"),
        @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        user = service.save(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Get users paginated")
    @GetMapping("/page")
    public ResponseEntity<Page<UserDTO>> getPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer pageSize,
            @RequestParam(value = "orderBy", defaultValue = "email") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<UserDTO> dtos = service.findPage(page, pageSize, orderBy, direction).map(mapper::toDTO);
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Update user email")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "User updated"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @RequestBody User user,
            @Parameter(description = "User ID") @PathVariable Integer id) {
        user.setId(id);
        service.update(user);
        return ResponseEntity.noContent().build();
    }

}
