package com.example.study.lab10.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<OrderDetailDto> orderDetails;
    private Long paymentId;
}
