package com.semmed.turing.demo.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record GlobalErrorResponse(
        UUID requestId,
        String errorCode,
        String errorMessage,
        LocalDateTime timestamp
) {
}
