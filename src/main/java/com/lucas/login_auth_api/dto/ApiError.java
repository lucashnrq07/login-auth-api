package com.lucas.login_auth_api.dto;

import java.time.Instant;
import java.time.LocalDateTime;

public record ApiError(
        LocalDateTime timestamp,
        int status,
        String message
) {}


