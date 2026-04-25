package com.librework.modules.skill.presentation.controller;

import com.librework.common.response.ApiResponse;
import com.librework.modules.skill.application.dto.response.SkillCategoryResponse;
import com.librework.modules.skill.application.port.in.SkillCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/skill-categories")
@RequiredArgsConstructor
public class SkillCategoryController {
    private final SkillCategoryUseCase skillCategoryUseCase;

    @GetMapping
    public ApiResponse<List<SkillCategoryResponse>> getAll() {
        return ApiResponse.ok(skillCategoryUseCase.getAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<SkillCategoryResponse> getById(@PathVariable UUID id)
    {
        return ApiResponse.ok(skillCategoryUseCase.getById(id));
    }
}
