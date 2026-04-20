package com.librework.modules.profiles.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserProfileSummary {
    private UUID freelancerProfileId;
    private String freelancerAvaterUrl;
    private List<ClientProfileInfo> clientProfiles;

    @Data
    @Builder
    public static class ClientProfileInfo {
        private UUID id;
        private String displayName;
        private String avatarUrl;
    }
}
