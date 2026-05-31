package com.socodo.mechcalc.chain.dto.response;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ChainCalculationResponse {
    private UUID id;
    private Double p;
    private Double n;
    private Double u;
    private Double k;
    private Integer z1;
    private Integer z2;
    private Double n01;
    private Double pt;
    private Double allowablePower;
    private Double pc;
    private Double d0;
    private Double b0;
    private Double pcMax;
    private Double d1;
    private Double d2;
    private Double da1;
    private Double da2;
    private Double asb;
    private Double xsb;
    private Integer x;
    private Double a;
    private Double deltaA;
    private Double chainLength;
    private Instant updatedAt;
}
