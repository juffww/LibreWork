package com.librework.modules.identity.application.port.out;

import java.util.Date;
import java.util.UUID;

public interface TokenProviderPort {
    String generateToken(String userName, UUID userId);
    boolean isTokenValid(String token);
    String getJwtId(String token);
    Date getExpirationTime(String token);
}