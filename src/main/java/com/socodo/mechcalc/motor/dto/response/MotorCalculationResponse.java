package com.socodo.mechcalc.motor.dto.response;

import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Data
public class MotorCalculationResponse {
    private UUID id;
    private Double pWorking;
    private Double etaTotal;
    private Double pRequired;
    private Double nWorking;
    private Double nPreliminary;
    private Double uTotalReal;
    private Double uHReal;
    private Double unt;
    private Double u1;
    private Double u2;
    private Double ux;
    private MotorCatalogResponse selectedMotor;
    private KinematicTableResponse kinematicTable; 
    private Instant updatedAt;
}
