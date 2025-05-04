package com.example.study.lab10.repository;

import com.example.study.lab10.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    List<Order> findAllByCustomerId(Long customerId);
    void deleteByIdAndCustomerId(Long id, Long customerId);
}