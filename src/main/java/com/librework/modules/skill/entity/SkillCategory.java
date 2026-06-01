package com.librework.modules.skill.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "skill_categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillCategory {
    @Id
    private UUID id;

    private String name;

    @Column(unique = true, nullable = false)
    private String slug;

    private String iconUrl;
    private int sortOrder;
    private LocalDateTime createdAt;
}
