package com.librework.modules.profiles.dto.request;

import com.librework.modules.profiles.entity.Language;
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
