package com.librework.modules.profile.application.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClientProfileCreationRequest {
    private String companyName;
}
