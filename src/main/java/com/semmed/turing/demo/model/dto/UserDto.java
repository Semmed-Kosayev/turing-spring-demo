package com.semmed.turing.demo.model.dto;

import com.semmed.turing.demo.model.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserDto(Long id, String username, String email, UserStatus status) {
}
