package com.example.study.OrderService.service;

import com.example.study.OrderService.domain.entity.order.Order;
import com.example.study.OrderService.domain.enums.OrderStatus;
import com.example.study.OrderService.domain.enums.PaymentType;
import com.example.study.OrderService.domain.enums.PaymentStatus;
import com.example.study.OrderService.dto.OrderDTO;
import com.example.study.OrderService.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderSelectionService {
    private final OrderRepository repository;

    @Autowired
    public  OrderSelectionService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public List<Order> getOrdersByCustomerAddressCity(String city) {
        return repository.findByCustomerAddressCity(city);
    }

    public List<Order> getOrdersByDateBetween(LocalDateTime start, LocalDateTime end) {
        return repository.findByDateBetween(start, end);
    }

    public List<Order> getOrdersByPaymentType(PaymentType type) {
        return repository.findByPaymentsPaymentType(type);
    }

    public List<Order> getOrdersByCustomerName(String name) {
        return repository.findByCustomerName(name);
    }

    public List<Order> getOrdersByPaymentStatus(PaymentStatus status) {
        return repository.findByPaymentsPaymentStatus(status);
    }

    public List<Order> getOrdersByOrderStatus(OrderStatus status) {
        return repository.findByOrderStatus(status);
    }

//    public Order createOrder(OrderDTO orderDTO) {
//
//    }
}

