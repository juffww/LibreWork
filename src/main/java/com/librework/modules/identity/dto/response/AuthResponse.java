package com.librework.modules.identity.dto.response;

import com.librework.common.enums.UserStatus;
import com.librework.modules.identity.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
    @JsonIgnore
    private String refreshToken;
    private UUID userId;
    private String email;
    private String username;

    @JsonProperty("full_name")
    private String fullName;
    private UserStatus status;
}
