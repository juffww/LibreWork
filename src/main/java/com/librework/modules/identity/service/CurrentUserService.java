package com.librework.modules.identity.service;

import com.cloudinary.api.exceptions.ApiException;
import com.librework.common.enums.ProfileType;
import com.librework.exception.AppException;
import com.librework.exception.ErrorCode;
import com.librework.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CurrentUserService {
    public UUID getCurrentUserId() {
        return UUID.fromString(SecurityUtils.getCurrentJwt().getClaim("userId"));
    }

    public String getCurrentUserName() {
        return SecurityUtils.getCurrentJwt().getClaim("userName");
    }

    public ProfileType getCurrentActiveProfileType()
    {
        String value = SecurityUtils.getCurrentJwt().getClaimAsString("activeProfileType");

        if(value == null)
        {
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        return ProfileType.valueOf(value);
    }
}
