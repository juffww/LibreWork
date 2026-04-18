package com.librework.modules.profiles.service;

import com.librework.modules.profiles.dto.response.UserProfileSummary;
import java.util.UUID;

public interface ProfileQueryService {
    UserProfileSummary getUserProfiles(UUID userId);
}
