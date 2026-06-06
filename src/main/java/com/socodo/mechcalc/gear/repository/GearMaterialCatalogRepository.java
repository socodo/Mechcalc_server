package com.socodo.mechcalc.gear.repository;

import com.socodo.mechcalc.gear.entity.GearMaterialCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GearMaterialCatalogRepository extends JpaRepository<GearMaterialCatalog, Long> {
}
