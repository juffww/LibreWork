package com.librework.modules.identity.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisTokenBlacklistService {

    private final StringRedisTemplate redisTemplate;
    private static final String BLACKLIST_PREFIX = "jwt:blacklist:";

    public void addToBlacklist(String jti, long remainingTimeMillis) {
        redisTemplate.opsForValue().set(
                BLACKLIST_PREFIX + jti,
                "revoked",
                Duration.ofMillis(remainingTimeMillis)
        );
    }

    public boolean isBlacklisted(String jti) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(BLACKLIST_PREFIX + jti));
    }
}
