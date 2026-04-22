package com.librework.modules.profile.application.dto.response;

import com.librework.common.enums.ProfileType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProfileResponse<T> {
    private ProfileType profileType;
    private T profile;
}
