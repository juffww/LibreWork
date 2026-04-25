package com.librework.modules.skill.presentation.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.skill.application.dto.response.SkillResponse;
import com.librework.modules.skill.application.port.in.SkillUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/skills")
public class SkillController {
    private final SkillUseCase skillUseCase;

    @GetMapping("/{id}")
    public ApiResponse<SkillResponse> getById(@PathVariable UUID id) {
        return ApiResponse.ok(skillUseCase.getById(id));
    }

    @GetMapping
    public ApiResponse<List<SkillResponse>> getAll(
            @RequestParam(required = false) UUID categoryId
    ) {
        if (categoryId != null) {
            return ApiResponse.ok(skillUseCase.getByCategoryId(categoryId));
        }
        return ApiResponse.ok(skillUseCase.getAll());
    }

}
