package com.example.study.lab10.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShippingWeightDto {
    private String name;
    private String symbol;
    private BigDecimal value;
}
