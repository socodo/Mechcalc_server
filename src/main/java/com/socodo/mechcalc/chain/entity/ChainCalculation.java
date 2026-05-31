package com.socodo.mechcalc.chain.entity;

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
@Table(name = "chain_calculations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChainCalculation {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "p")
    private Double p;

    @Column(name = "n")
    private Double n;

    @Column(name = "u")
    private Double u;

    @Column(name = "k")
    private Double k;

    @Column(name = "z_1")
    private Integer z1;

    @Column(name = "z_2")
    private Integer z2;

    @Column(name = "n_01")
    private Double n01;

    @Column(name = "p_t")
    private Double pt;

    @Column(name = "allowable_power")
    private Double allowablePower;

    @Column(name = "p_c")
    private Double pc;

    @Column(name = "d_0")
    private Double d0;

    @Column(name = "b_0")
    private Double b0;

    @Column(name = "p_c_max")
    private Double pcMax;

    @Column(name = "d_1")
    private Double d1;

    @Column(name = "d_2")
    private Double d2;

    @Column(name = "d_a1")
    private Double da1;

    @Column(name = "d_a2")
    private Double da2;

    @Column(name = "a_sb")
    private Double asb;

    @Column(name = "x_sb")
    private Double xsb;

    @Column(name = "x")
    private Integer x;

    @Column(name = "a")
    private Double a;

    @Column(name = "delta_a")
    private Double deltaA;

    @Column(name = "chain_length")
    private Double chainLength;

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
