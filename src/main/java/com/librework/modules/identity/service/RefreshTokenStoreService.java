package com.librework.modules.identity.service;

import java.time.Duration;
import java.util.UUID;

public interface RefreshTokenStoreService {
    void store(UUID userId, String jwtId, Duration ttl);
    boolean isCurrent(UUID userId, String jwtId);
    void revoke(UUID userId);
}
