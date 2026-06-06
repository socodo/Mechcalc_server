package com.socodo.mechcalc.project.controller;

import com.socodo.mechcalc.common.dto.response.ApiResponse;
import com.socodo.mechcalc.exception.AppException;
import com.socodo.mechcalc.exception.ErrorCode;
import com.socodo.mechcalc.project.dto.request.ProjectSaveRequest;
import com.socodo.mechcalc.project.dto.response.ProjectResponse;
import com.socodo.mechcalc.project.service.ProjectService;
import com.socodo.mechcalc.user.entity.User;
import com.socodo.mechcalc.user.repository.UserRepository;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects") 
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectController {

    ProjectService projectService;
    UserRepository userRepository; 

    @PostMapping("/push")
    public ApiResponse<String> saveProjects(@Valid @RequestBody List<ProjectSaveRequest> requests) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
                
        projectService.saveProjects(requests, user.getId());

        return ApiResponse.success("Lưu danh sách dự án thành công", "Dữ liệu dự án đã được lưu.");
    }

    @GetMapping
    public ApiResponse<List<ProjectResponse>> getMyProjects() {
        User user = getCurrentUser();

        List<ProjectResponse> projects = projectService.getMyProjects(user.getId());
        return ApiResponse.success("Lấy danh sách dự án thành công", projects);
    }

    @PostMapping
    public ApiResponse<ProjectResponse> createProject(@Valid @RequestBody ProjectSaveRequest request) {
        ProjectResponse project = projectService.createProject(request, getCurrentUser().getId());
        return ApiResponse.success("Tạo dự án thành công", project);
    }

    @GetMapping("/{projectId}")
    public ApiResponse<ProjectResponse> getProject(@PathVariable UUID projectId) {
        ProjectResponse project = projectService.getProject(projectId, getCurrentUser().getId());
        return ApiResponse.success("Lấy thông tin dự án thành công", project);
    }

    @PutMapping("/{projectId}")
    public ApiResponse<ProjectResponse> updateProject(
            @PathVariable UUID projectId,
            @Valid @RequestBody ProjectSaveRequest request
    ) {
        ProjectResponse project = projectService.updateProject(projectId, request, getCurrentUser().getId());
        return ApiResponse.success("Cập nhật dự án thành công", project);
    }

    @DeleteMapping("/{projectId}")
    public ApiResponse<Void> deleteProject(@PathVariable UUID projectId) {
        projectService.deleteProject(projectId, getCurrentUser().getId());
        return ApiResponse.success("Xóa dự án thành công");
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
}
