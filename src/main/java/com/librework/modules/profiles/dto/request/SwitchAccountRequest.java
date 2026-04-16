package com.librework.modules.profiles.dto.request;

import com.librework.common.ProfileType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SwitchAccountRequest {
    private ProfileType profileType;
    private UUID targetClientProfileId;
}
