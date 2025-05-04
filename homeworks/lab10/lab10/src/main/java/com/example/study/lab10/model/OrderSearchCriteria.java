package com.example.study.lab10.model;

import lombok.Data;
import com.example.study.lab10.model.entity.Address;
import com.example.study.lab10.model.entity.payment.Payment;
import com.example.study.lab10.model.entity.payment.PaymentStatus;

import java.time.LocalDateTime;

// Критерии для поиска
@Data
public class OrderSearchCriteria {
    private Address address;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Class<? extends Payment> payment;
    private String customerName;
    private PaymentStatus paymentStatus;
    private String orderStatus;
}
