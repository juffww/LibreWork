package com.librework.modules.profiles.dto.response;

import com.librework.common.ProfileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ProfileResponse {
    private ProfileType profileType;
    private Object profile;
}
