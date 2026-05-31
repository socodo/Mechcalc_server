package com.socodo.mechcalc.motor.dto.request;

import com.socodo.mechcalc.motor.dto.response.KinematicTableResponse;
import lombok.Data;
import java.util.UUID;

@Data
public class MotorCalculationRequest {
    private UUID id;
    private UUID projectId;
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
    private Long selectedMotorId;
    private KinematicTableResponse kinematicTable; 
}
