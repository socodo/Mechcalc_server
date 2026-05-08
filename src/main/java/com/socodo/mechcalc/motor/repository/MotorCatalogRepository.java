package com.socodo.mechcalc.motor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.socodo.mechcalc.motor.entity.MotorCatalog;

@Repository
public interface MotorCatalogRepository extends JpaRepository<MotorCatalog, Long> {
}