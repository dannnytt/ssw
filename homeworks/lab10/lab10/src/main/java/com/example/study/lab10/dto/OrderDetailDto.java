package com.example.study.lab10.dto;

import lombok.Data;

@Data
public class OrderDetailDto {
    private QuantityDto quantity;
    private String taxStatus;
    private Long itemId;
}
