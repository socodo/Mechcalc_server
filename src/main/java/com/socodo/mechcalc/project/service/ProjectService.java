package com.socodo.mechcalc.project.service;

import com.socodo.mechcalc.exception.AppException;
import com.socodo.mechcalc.exception.ErrorCode;
import com.socodo.mechcalc.project.dto.request.ProjectSaveRequest;
import com.socodo.mechcalc.project.dto.response.ProjectResponse;
import com.socodo.mechcalc.project.entity.Project;
import com.socodo.mechcalc.project.mapper.ProjectMapper;
import com.socodo.mechcalc.project.repository.ProjectRepository;
import com.socodo.mechcalc.user.entity.User;
import com.socodo.mechcalc.user.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectService {

    ProjectRepository projectRepository;
    UserRepository userRepository;
    ProjectMapper projectMapper;

    @Transactional(readOnly = true)
    public List<ProjectResponse> getMyProjects(UUID userId) {
        return projectRepository.findAllByUserIdAndDeletedAtIsNull(userId)
                .stream()
                .map(projectMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProjectResponse getProject(UUID projectId, UUID userId) {
        return projectRepository.findByIdAndUserIdAndDeletedAtIsNull(projectId, userId)
                .map(projectMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));
    }

    @Transactional
    public ProjectResponse createProject(ProjectSaveRequest request, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        UUID projectId = request.getId() != null ? request.getId() : UUID.randomUUID();
        if (projectRepository.existsById(projectId)) {
            throw new AppException(ErrorCode.INVALID_REQUEST, "Mã dự án đã tồn tại");
        }

        Project project = new Project();
        project.setId(projectId);
        project.setUser(user);
        applyProjectFields(project, request);

        return projectMapper.toResponse(projectRepository.save(project));
    }

    @Transactional
    public ProjectResponse updateProject(UUID projectId, ProjectSaveRequest request, UUID userId) {
        Project project = projectRepository.findByIdAndUserIdAndDeletedAtIsNull(projectId, userId)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));

        applyProjectFields(project, request);
        return projectMapper.toResponse(projectRepository.save(project));
    }

    @Transactional
    public void deleteProject(UUID projectId, UUID userId) {
        Project project = projectRepository.findByIdAndUserIdAndDeletedAtIsNull(projectId, userId)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));

        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }

    private void applyProjectFields(Project project, ProjectSaveRequest request) {
        if (!StringUtils.hasText(request.getName())) {
            throw new AppException(ErrorCode.INVALID_REQUEST, "Tên dự án không được để trống");
        }

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStatus(parseStatus(request.getStatus()));
        project.setDeletedAt(request.getDeletedAt());
    }

    private Project.ProjectStatus parseStatus(String status) {
        if (!StringUtils.hasText(status)) {
            return Project.ProjectStatus.IN_PROGRESS;
        }

        try {
            return Project.ProjectStatus.valueOf(status);
        } catch (IllegalArgumentException exception) {
            throw new AppException(ErrorCode.INVALID_REQUEST, "Trạng thái dự án không hợp lệ");
        }
    }
}
