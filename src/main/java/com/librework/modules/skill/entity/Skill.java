package com.librework.modules.skill.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "skills")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    @Id
    private UUID id;

    @Column(name = "category_id")
    private UUID categoryId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(name = "is_verified")
    private boolean isVerified;

    private LocalDateTime createdAt;

    public void verify() {
        this.isVerified = true;
    }
}
