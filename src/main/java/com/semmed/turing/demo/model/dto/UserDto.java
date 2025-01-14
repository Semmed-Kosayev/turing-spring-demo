package com.semmed.turing.demo.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDto(
        Long id,

        @NotBlank
        @Size(max = 60)
        String username,

        @Email
        @Size(max = 100)
        String email,

        @Size(min = 6, max = 8)
        String status
) {
}
