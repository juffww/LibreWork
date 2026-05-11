package com.librework.common.port;

import java.util.UUID;

public interface FreelancerProfileQueryPort {
    /**
     * Resolve userId → freelancerProfileId.
     * Throws AppException(FREELANCER_PROFILE_NOT_FOUND) nếu không tìm thấy.
     */
    UUID findIdByUserId(UUID userId);
}