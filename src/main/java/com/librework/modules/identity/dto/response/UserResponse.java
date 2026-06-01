package com.librework.modules.identity.dto.response;

import com.librework.common.enums.UserStatus;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String email;
    private String username;
    private String fullName;
    private UserStatus status;
}
