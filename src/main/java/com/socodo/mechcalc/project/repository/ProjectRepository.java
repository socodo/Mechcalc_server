package com.socodo.mechcalc.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.socodo.mechcalc.project.entity.Project;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findAllByUserIdAndDeletedAtIsNull(UUID userId);
}