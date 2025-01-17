package com.semmed.turing.demo.model.dto;

import com.semmed.turing.demo.model.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @NotBlank
        @Size(max = 60)
        String username,

        @Email
        @NotBlank
        @Size(max = 100)
        String email,

        @NotBlank
        @Size(min = 6, max = 50)
        String password,

        @NotNull
        UserStatus status
) {
}
