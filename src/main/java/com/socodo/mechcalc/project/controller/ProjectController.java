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
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<ProjectResponse> projects = projectService.getMyProjects(user.getId());
        return ApiResponse.success("Lấy danh sách dự án thành công", projects);
    }
}
