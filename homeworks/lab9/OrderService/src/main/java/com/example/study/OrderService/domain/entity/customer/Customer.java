package com.example.study.OrderService.domain.entity.customer;

import com.example.study.OrderService.domain.entity.order.Order;
import com.example.study.OrderService.domain.value.address.Address;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    List<Order> orders = new ArrayList<>();
}

