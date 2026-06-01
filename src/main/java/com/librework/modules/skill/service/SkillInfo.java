package com.librework.modules.skill.service;

import java.util.UUID;

public record SkillInfo(
        UUID id,
        String name,
        String slug
) {
}
