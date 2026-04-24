package com.librework.modules.profile.application.dto.request;

import com.librework.common.enums.ProfileType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SwitchAccountRequest {
    private ProfileType targetType;
}
