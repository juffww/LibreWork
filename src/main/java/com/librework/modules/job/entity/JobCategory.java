package com.librework.modules.job.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "job_categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobCategory {
    @Id
    private UUID id;

    @Column(name = "parent_id")
    private UUID parentId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100, unique = true)
    private String slug;

    @Column(name = "icon_url", length = 500)
    private String iconUrl;

    private String description;

    @Column(name = "sort_order")
    private int sortOrder;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public void update(String name, String slug, String iconUrl, String description, int sortOrder) {
        this.name = name;
        this.slug = slug;
        this.iconUrl = iconUrl;
        this.description = description;
        this.sortOrder = sortOrder;
    }
}
