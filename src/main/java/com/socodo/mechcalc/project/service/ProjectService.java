package com.socodo.mechcalc.project.service;

import com.socodo.mechcalc.project.dto.request.ProjectSyncRequest;
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
    public void syncProjects(List<ProjectSyncRequest> requests, UUID userId) {
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

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
}
