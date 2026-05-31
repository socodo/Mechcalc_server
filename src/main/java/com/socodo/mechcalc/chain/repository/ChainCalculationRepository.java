package com.socodo.mechcalc.chain.repository;

import com.socodo.mechcalc.chain.entity.ChainCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChainCalculationRepository extends JpaRepository<ChainCalculation, UUID> {
    Optional<ChainCalculation> findByProjectIdAndProjectUserEmail(UUID projectId, String email);
}
