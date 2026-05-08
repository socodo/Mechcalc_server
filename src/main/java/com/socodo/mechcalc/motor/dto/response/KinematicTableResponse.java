package com.socodo.mechcalc.motor.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KinematicTableResponse {
    private KinematicRow motor;        // Động cơ
    private KinematicRow shaft1;       // Trục I
    private KinematicRow shaft2;       // Trục II
    private KinematicRow shaft3;       // Trục III
    private KinematicRow workingShaft; // Trục công tác
}