package com.librework.modules.identity.service;

public interface TokenBlacklistService {
    void addToBlacklist(String jti, long remainingTimeMillis);
    boolean isBlacklisted(String jti);
}