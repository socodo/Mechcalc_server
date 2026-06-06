package com.socodo.mechcalc.gear.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class GearCalculationRequest {
    private UUID id;
    private UUID projectId;
    private Double nI;
    private Double nII;
    private Double tI;
    private Double tII;
    private Double u1;
    private Double u2;
    private Double lifeHours;
    private Double allowableSigmaH;
    private Double allowableSigmaF;
    private Integer fastZ1;
    private Integer fastZ2;
    private Double fastMte;
    private Double fastMtm;
    private Double fastMnm;
    private Double fastRe;
    private Double fastB;
    private Double fastDm1;
    private Double fastDm2;
    private Double fastDelta1;
    private Double fastDelta2;
    private Double fastSigmaH;
    private Double fastSigmaF1;
    private Double fastSigmaF2;
    private Double fastFt1;
    private Double fastFr1;
    private Double fastFa1;
    private String fastWarning;
    private Integer slowZ1;
    private Integer slowZ2;
    private Double slowM;
    private Double slowAw;
    private Double slowBw;
    private Double slowDw1;
    private Double slowDw2;
    private Double slowDa1;
    private Double slowDa2;
    private Double slowDf1;
    private Double slowDf2;
    private Double slowSigmaH;
    private Double slowSigmaF1;
    private Double slowSigmaF2;
    private Double slowFt1;
    private Double slowFr1;
    private String slowWarning;
}
