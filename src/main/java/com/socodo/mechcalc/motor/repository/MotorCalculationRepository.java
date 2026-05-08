package com.socodo.mechcalc.motor.repository;

import com.socodo.mechcalc.motor.entity.MotorCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MotorCalculationRepository extends JpaRepository<MotorCalculation, UUID> {
    Optional<MotorCalculation> findByProjectId(UUID projectId);
}