package com.socodo.mechcalc.motor.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Data
public class MotorCalculationResponse {
    private UUID id;
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
    private MotorCatalogResponse selectedMotor;
    private KinematicTableResponse kinematicTable; 
    private Instant updatedAt;
}
