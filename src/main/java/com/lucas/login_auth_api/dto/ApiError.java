package com.lucas.login_auth_api.dto;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public record ApiError(
        Instant timestamp,
        int status,
        String message
) {}


