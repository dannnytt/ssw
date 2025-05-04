package com.example.study.lab10.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuantityDto {
    private Integer value;
    private String name;
    private String symbol;
}
