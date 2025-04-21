package com.sibsutis.study.lab7.repository;

import com.sibsutis.study.lab7.domain.entity.order.Order;
import com.sibsutis.study.lab7.domain.enums.OrderStatus;
import com.sibsutis.study.lab7.domain.enums.PaymentStatus;
import com.sibsutis.study.lab7.domain.enums.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByCustomer_Address_City(String city);

    List<Order> findByDateBetween(LocalDateTime start, LocalDateTime end);

    List<Order> findByPayments_PaymentType(PaymentType paymentType);

    List<Order> findByCustomer_Name(String customerName);

    List<Order> findByPayments_PaymentStatus(PaymentStatus status);

    List<Order> findByOrderStatus(OrderStatus status);


}
