package com.librework.common.port;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SkillQueryPort {
    boolean existsById(UUID skillId);
    Optional<SkillInfo> findById(UUID skillId);
    List<SkillInfo> findAllByIds(List<UUID> ids);
}