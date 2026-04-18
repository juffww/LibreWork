package com.librework.modules.identity.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.librework.modules.identity.entity.User.UserStatus;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
    private UUID userId;
    private String email;
    private String username;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    private UserStatus status;

    private UUID freelancerProfileId;

    private List<ClientProfileSummary> clientProfiles;

    @Data
    @Builder
    public static class ClientProfileSummary {
        private UUID id;
        private String displayName;

        @JsonProperty("avatar_url")
        private String avatarUrl;
    }
}
