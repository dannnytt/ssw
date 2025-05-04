package com.example.study.lab10.dto;

import lombok.Data;

@Data
public class ItemRequest {
    private ShippingWeightDto shippingWeight;
    private String description;
}
