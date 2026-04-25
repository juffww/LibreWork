package com.librework.modules.skill.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    private UUID id;
    private UUID categoryId;
    private String name;
    private String slug;
    private boolean is_verified;
    private LocalDateTime createdAt;

    public void verify() {
        this.is_verified = true;
    }
}
