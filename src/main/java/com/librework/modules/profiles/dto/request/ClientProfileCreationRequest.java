package com.librework.modules.profiles.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClientProfileCreationRequest {
    private String companyName;
}
