package com.librework.modules.identity.application.dto.request;

import com.librework.common.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingUpdateRequest {
    private String country;
    private Language language;
    private String timezone;
}
