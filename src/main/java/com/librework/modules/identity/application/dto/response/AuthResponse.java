package com.librework.modules.identity.application.dto.response;

import com.librework.common.enums.UserStatus;
import com.librework.modules.identity.domain.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
    private UUID userId;
    private String email;
    private String username;

    @JsonProperty("full_name")
    private String fullName;
    private UserStatus status;
}
