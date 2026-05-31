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

    @Transactional
    public void saveProjects(List<ProjectSaveRequest> requests, UUID userId) {
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        requests.forEach(request -> validateProjectOwnership(request, userId));

        List<Project> projectsToSave = requests.stream()
                .map(req -> projectMapper.toEntity(req, user))
                .collect(Collectors.toList());

        projectRepository.saveAll(projectsToSave);
    }

    public List<ProjectResponse> getMyProjects(UUID userId) {
        return projectRepository.findAllByUserIdAndDeletedAtIsNull(userId)
                .stream()
                .map(projectMapper::toResponse)
                .collect(Collectors.toList());
    }

    private void validateProjectOwnership(ProjectSaveRequest request, UUID userId) {
        if (request.getId() == null) {
            throw new AppException(ErrorCode.INVALID_REQUEST, "Mã dự án không được để trống");
        }

        boolean projectExists = projectRepository.existsById(request.getId());
        if (projectExists && !projectRepository.existsByIdAndUserId(request.getId(), userId)) {
            throw new AppException(ErrorCode.FORBIDDEN, "Không thể lưu dự án của người dùng khác");
        }
    }
}
