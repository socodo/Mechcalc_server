package com.socodo.mechcalc.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.socodo.mechcalc.project.entity.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findAllByUserIdAndDeletedAtIsNull(UUID userId);
    Optional<Project> findByIdAndUserEmail(UUID id, String email);
    Optional<Project> findByIdAndUserIdAndDeletedAtIsNull(UUID id, UUID userId);
    Optional<Project> findByIdAndUserEmailAndDeletedAtIsNull(UUID id, String email);
    boolean existsByIdAndUserId(UUID id, UUID userId);
}
