package com.librework.modules.identity.infrastructure.security;

import com.librework.common.exception.AppException;
import com.librework.common.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public class SecurityUtils {
    private SecurityUtils() {
    }
    // Extract email from jwt token
    public static Jwt getCurrentJwt()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated())
        {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // princial: Thông tin chính của user 
        if(authentication.getPrincipal() instanceof Jwt jwt)
        {
            return jwt;
        }

        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

    public static UUID getCurrentUserId()
    {
        Jwt jwt = getCurrentJwt();
        return UUID.fromString(jwt.getClaim("userId"));
    }
}
