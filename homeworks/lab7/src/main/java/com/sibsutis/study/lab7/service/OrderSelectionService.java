package com.sibsutis.study.lab7.service;

import com.sibsutis.study.lab7.domain.entity.order.Order;
import com.sibsutis.study.lab7.domain.enums.OrderStatus;
import com.sibsutis.study.lab7.domain.enums.PaymentStatus;
import com.sibsutis.study.lab7.domain.enums.PaymentType;
import com.sibsutis.study.lab7.repository.OrderRepository;
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
}
