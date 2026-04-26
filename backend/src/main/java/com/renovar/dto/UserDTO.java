package com.renovar.dto;

import jakarta.validation.constraints.Email;

public record UserDTO(
        Integer id,
        @Email(message = "Invalid email") String email
) {}