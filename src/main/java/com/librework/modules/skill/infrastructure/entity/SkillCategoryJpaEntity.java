package com.librework.modules.skill.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class SkillCategoryJpaEntity {

    @Id
    private UUID id;
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;

    private String iconUrl;
    private int sortOrder;
    private LocalDateTime createdAt;
}
