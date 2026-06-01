package com.librework.modules.identity.service;

import com.librework.common.enums.ProfileType;

import java.util.Date;
import java.util.UUID;

public interface TokenProviderService {
    String generateToken(String userName, UUID userId);
    String generateToken(String userName, UUID userId, ProfileType activeProfileType);
    String generateRefreshToken(String userName, UUID userId);
    boolean isTokenValid(String token);
    boolean isRefreshTokenValid(String token);
    String getJwtId(String token);
    Date getExpirationTime(String token);
    UUID getUserId(String token);
    String getUsername(String token);
}
