package com.socodo.mechcalc.motor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "motor_catalogs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotorCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "motor_code", nullable = false, unique = true, length = 50)
    private String motorCode;

    @Column(name = "series", length = 20)
    private String series;

    @Column(name = "power")
    private Double power;

    @Column(name = "speed")
    private Integer speed;

    @Column(name = "sync_speed")
    private Integer syncSpeed;

    @Column(name = "poles")
    private Integer poles;

    @Column(name = "efficiency")
    private Double efficiency;

    @Column(name = "cos_phi")
    private Double cosPhi;

    @Column(name = "tk_tdn_ratio")
    private Double tkTdnRatio;

    @Column(name = "tmax_tdn_ratio")
    private Double tmaxTdnRatio;

    @Column(name = "inertia")
    private Double inertia;

    @Column(name = "weight_kg")
    private Double weight;
}