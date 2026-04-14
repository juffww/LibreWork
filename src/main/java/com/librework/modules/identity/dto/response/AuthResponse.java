package com.librework.modules.identity.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
    private UUID userId;
    private String email;
}
