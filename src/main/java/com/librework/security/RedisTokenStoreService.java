package com.librework.security;

import com.librework.modules.identity.service.TokenBlacklistService;
import com.librework.modules.identity.service.RefreshTokenStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RedisTokenStoreService implements TokenBlacklistService, RefreshTokenStoreService {

    private final StringRedisTemplate redisTemplate;

    private static final String BLACKLIST_PREFIX = "jwt:blacklist:";
    private static final String REFRESH_PREFIX = "jwt:refresh:";

    @Override
    public void addToBlacklist(String jti, long remainingTimeMillis) {
        redisTemplate.opsForValue().set(
                BLACKLIST_PREFIX + jti,
                "revoked", // Giá trị không quan trọng, chỉ cần có key tồn tại
                Duration.ofMillis(remainingTimeMillis)
        );
    }

    @Override
    public boolean isBlacklisted(String jti) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(BLACKLIST_PREFIX + jti));
    }

    @Override
    public void store(UUID userId, String jwtId, Duration ttl) {
        redisTemplate.opsForValue().set(REFRESH_PREFIX + userId, jwtId, ttl);
    }

    @Override
    public boolean isCurrent(UUID userId, String jwtId) {
        String storedJwtId = redisTemplate.opsForValue().get(REFRESH_PREFIX + userId);
        return jwtId != null && jwtId.equals(storedJwtId);
    }

    @Override
    public void revoke(UUID userId) {
        redisTemplate.delete(REFRESH_PREFIX + userId);
    }
}
