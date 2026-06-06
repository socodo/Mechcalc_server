package com.socodo.mechcalc.gear.entity;

import com.socodo.mechcalc.project.entity.Project;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "gear_calculations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GearCalculation {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "n_i")
    private Double nI;

    @Column(name = "n_ii")
    private Double nII;

    @Column(name = "t_i")
    private Double tI;

    @Column(name = "t_ii")
    private Double tII;

    @Column(name = "u_1")
    private Double u1;

    @Column(name = "u_2")
    private Double u2;

    @Column(name = "life_hours")
    private Double lifeHours;

    @Column(name = "allowable_sigma_h")
    private Double allowableSigmaH;

    @Column(name = "allowable_sigma_f")
    private Double allowableSigmaF;

    @Column(name = "fast_z_1")
    private Integer fastZ1;

    @Column(name = "fast_z_2")
    private Integer fastZ2;

    @Column(name = "fast_m_te")
    private Double fastMte;

    @Column(name = "fast_m_tm")
    private Double fastMtm;

    @Column(name = "fast_m_nm")
    private Double fastMnm;

    @Column(name = "fast_r_e")
    private Double fastRe;

    @Column(name = "fast_b")
    private Double fastB;

    @Column(name = "fast_d_m1")
    private Double fastDm1;

    @Column(name = "fast_d_m2")
    private Double fastDm2;

    @Column(name = "fast_delta_1")
    private Double fastDelta1;

    @Column(name = "fast_delta_2")
    private Double fastDelta2;

    @Column(name = "fast_sigma_h")
    private Double fastSigmaH;

    @Column(name = "fast_sigma_f1")
    private Double fastSigmaF1;

    @Column(name = "fast_sigma_f2")
    private Double fastSigmaF2;

    @Column(name = "fast_f_t1")
    private Double fastFt1;

    @Column(name = "fast_f_r1")
    private Double fastFr1;

    @Column(name = "fast_f_a1")
    private Double fastFa1;

    @Column(name = "fast_warning", columnDefinition = "TEXT")
    private String fastWarning;

    @Column(name = "slow_z_1")
    private Integer slowZ1;

    @Column(name = "slow_z_2")
    private Integer slowZ2;

    @Column(name = "slow_m")
    private Double slowM;

    @Column(name = "slow_a_w")
    private Double slowAw;

    @Column(name = "slow_b_w")
    private Double slowBw;

    @Column(name = "slow_d_w1")
    private Double slowDw1;

    @Column(name = "slow_d_w2")
    private Double slowDw2;

    @Column(name = "slow_d_a1")
    private Double slowDa1;

    @Column(name = "slow_d_a2")
    private Double slowDa2;

    @Column(name = "slow_d_f1")
    private Double slowDf1;

    @Column(name = "slow_d_f2")
    private Double slowDf2;

    @Column(name = "slow_sigma_h")
    private Double slowSigmaH;

    @Column(name = "slow_sigma_f1")
    private Double slowSigmaF1;

    @Column(name = "slow_sigma_f2")
    private Double slowSigmaF2;

    @Column(name = "slow_f_t1")
    private Double slowFt1;

    @Column(name = "slow_f_r1")
    private Double slowFr1;

    @Column(name = "slow_warning", columnDefinition = "TEXT")
    private String slowWarning;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false, unique = true)
    private Project project;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
