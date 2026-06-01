package com.librework.modules.profile.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.librework.common.enums.ProfileType;
import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserProfileSummary {
    private UUID userId;
    private ProfileType activeProfileType;
    private String accessToken;

    private FreelancerInfo freelancer;
    private ClientInfo client;

    @Data
    @Builder
    public static class FreelancerInfo {
        private UUID profileId;
        private String displayName;
        private String avatarUrl;
    }

    @Data
    @Builder
    public static class ClientInfo {
        private UUID profileId;
        private String displayName;
    }
}
