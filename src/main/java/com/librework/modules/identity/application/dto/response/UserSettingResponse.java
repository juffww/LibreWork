package com.librework.modules.identity.application.dto.response;

import com.librework.common.enums.Language;
import com.librework.common.enums.ProfileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingResponse {
    private UUID userId;
    private String timezone;
    private String country;
    private Language language;
    private ProfileType activeProfileType;
}

