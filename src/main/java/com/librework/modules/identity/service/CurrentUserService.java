package com.librework.modules.identity.service;

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
}
