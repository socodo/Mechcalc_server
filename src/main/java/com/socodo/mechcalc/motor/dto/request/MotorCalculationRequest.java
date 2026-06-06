package com.socodo.mechcalc.motor.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.socodo.mechcalc.motor.dto.response.KinematicTableResponse;
import lombok.Data;
import java.util.UUID;

@Data
public class MotorCalculationRequest {
    private UUID id;
    private UUID projectId;
    @JsonProperty("pWorking")
    private Double pWorking;
    private Double etaTotal;
    @JsonProperty("pRequired")
    private Double pRequired;
    @JsonProperty("nWorking")
    private Double nWorking;
    @JsonProperty("nPreliminary")
    private Double nPreliminary;
    @JsonProperty("uTotalReal")
    private Double uTotalReal;
    @JsonProperty("uHReal")
    private Double uHReal;
    private Double unt;
    private Double u1;
    private Double u2;
    private Double ux;
    private Long selectedMotorId;
    private KinematicTableResponse kinematicTable; 
}
