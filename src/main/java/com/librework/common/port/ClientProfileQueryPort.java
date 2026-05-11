package com.librework.common.port;

import java.util.UUID;

public interface ClientProfileQueryPort {
    /**
     * Resolve userId → clientProfileId.
     * Throws AppException(CLIENT_PROFILE_NOT_FOUND) nếu không tìm thấy.
     */
    UUID findIdByUserId(UUID userId);
}