package com.librework.modules.identity.infrastructure.adapter;

import com.librework.common.port.CurrentUserPort;
import com.librework.modules.identity.infrastructure.security.SecurityUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CurrentUserAdapter implements CurrentUserPort {
    @Override
    public UUID getCurrentUserId() {
        return UUID.fromString(SecurityUtils.getCurrentJwt().getClaim("userId"));
    }

    @Override
    public String getCurrentUserName() {
        return SecurityUtils.getCurrentJwt().getClaim("userName");
    }
}
