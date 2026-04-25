package com.librework.modules.skill.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class SkillCategory {
    private UUID id;
    private String name;
    private String slug;
    private String iconUrl;
    private int sortOrder;
    private LocalDateTime createdAt;
}
