package com.librework.modules.job.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class JobCategory {
    private UUID id;
    private UUID parentId;      // null = root category
    private String name;
    private String slug;
    private String iconUrl;
    private String description;
    private int sortOrder;
    private LocalDateTime createdAt;

    public void update(String name, String slug, String iconUrl, String description, int sortOrder) {
        this.name = name;
        this.slug = slug;
        this.iconUrl = iconUrl;
        this.description = description;
        this.sortOrder = sortOrder;
    }
}