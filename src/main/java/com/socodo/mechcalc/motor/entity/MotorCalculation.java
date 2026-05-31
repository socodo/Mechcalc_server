package com.socodo.mechcalc.motor.entity;

import com.socodo.mechcalc.project.entity.Project;
import com.socodo.mechcalc.motor.dto.response.KinematicTableResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "motor_calculations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotorCalculation {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "p_working")
    private Double pWorking;

    @Column(name = "eta_total")
    private Double etaTotal;

    @Column(name = "p_required")
    private Double pRequired;

    @Column(name = "n_working")
    private Double nWorking;

    @Column(name = "n_preliminary")
    private Double nPreliminary;

    @Column(name = "u_total_real")
    private Double uTotalReal;

    @Column(name = "u_h_real")
    private Double uHReal;

    @Column(name = "u_nt")
    private Double unt;

    @Column(name = "u_1")
    private Double u1;

    @Column(name = "u_2")
    private Double u2;

    @Column(name = "u_x")
    private Double ux;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "kinematic_table", columnDefinition = "jsonb")
    private KinematicTableResponse kinematicTable;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false, unique = true)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selected_motor_id", referencedColumnName = "id")
    private MotorCatalog selectedMotor;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
