package com.semmed.turing.demo.model.dto;

import com.semmed.turing.demo.model.enums.UserStatus;

public record StatusRequest(
        UserStatus status
) {
}
