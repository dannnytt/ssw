package com.example.study.lab10.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private LocalDateTime date;
    private String status;
    private Long userId;
    private List<OrderDetailDto> orderDetails;
    private Long paymentId;
}
