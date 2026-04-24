package com.librework.modules.identity.application.port.out;

public interface TokenBlacklistPort {
    void addToBlacklist(String jti, long remainingTimeMillis);
    boolean isBlacklisted(String jti);
}