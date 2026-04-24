package com.librework.modules.identity.infrastructure.adapter;

import com.librework.modules.identity.application.port.out.TokenBlacklistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisTokenBlacklistAdapter implements TokenBlacklistPort {

    private final StringRedisTemplate redisTemplate;

    // Gợi ý: Bạn có thể đưa tiền tố này vào @Value trong application.yml để cấu hình linh hoạt hơn
    private static final String BLACKLIST_PREFIX = "jwt:blacklist:";

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
}