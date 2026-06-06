package com.socodo.mechcalc.gear.repository;

import com.socodo.mechcalc.gear.entity.GearCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GearCalculationRepository extends JpaRepository<GearCalculation, UUID> {
    Optional<GearCalculation> findByProjectIdAndProjectUserEmail(UUID projectId, String email);
}
