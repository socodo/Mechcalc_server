package com.socodo.mechcalc.motor.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KinematicRow {
    private Double power;  // Công suất P (kW)
    private Double ratio;  // Tỷ số truyền u
    private Double speed;  // Số vòng quay n (v/p)
    private Double torque; // Mô-men xoắn T (Nmm)
}