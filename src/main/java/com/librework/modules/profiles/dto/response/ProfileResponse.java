package com.librework.modules.profiles.dto.response;

import com.librework.common.ProfileType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProfileResponse<T> {
    private ProfileType profileType;
    private T profile;
}
