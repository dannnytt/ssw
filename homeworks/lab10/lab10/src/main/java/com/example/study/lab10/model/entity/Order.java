package com.example.study.lab10.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.example.study.lab10.model.entity.payment.Payment;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "\"order\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;
    private String status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;
}