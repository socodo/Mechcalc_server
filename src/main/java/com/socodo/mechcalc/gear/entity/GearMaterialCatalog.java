package com.socodo.mechcalc.gear.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "gear_catalogs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GearMaterialCatalog {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "catalog_code", nullable = false, unique = true, length = 100)
    private String catalogCode;

    @Column(name = "gear_detail", nullable = false, length = 100)
    private String gearDetail;

    @Column(name = "material", length = 100)
    private String material;

    @Column(name = "heat_treatment", length = 100)
    private String heatTreatment;

    @Column(name = "size_limit_mm")
    private Double sizeLimitMm;

    @Column(name = "hardness_hb")
    private Integer hardnessHb;

    @Column(name = "sigma_b")
    private Double sigmaB;

    @Column(name = "sigma_ch")
    private Double sigmaCh;

    @Column(name = "contact_fatigue_limit_coefficient")
    private Double contactFatigueLimitCoefficient;

    @Column(name = "contact_fatigue_limit_constant")
    private Double contactFatigueLimitConstant;

    @Column(name = "contact_safety_factor")
    private Double contactSafetyFactor;

    @Column(name = "bending_fatigue_limit_coefficient")
    private Double bendingFatigueLimitCoefficient;

    @Column(name = "bending_safety_factor")
    private Double bendingSafetyFactor;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
