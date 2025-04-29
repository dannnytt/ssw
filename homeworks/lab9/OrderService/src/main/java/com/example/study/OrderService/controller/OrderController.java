package com.example.study.OrderService.controller;

import com.example.study.OrderService.domain.enums.OrderStatus;
import com.example.study.OrderService.domain.enums.PaymentStatus;
import com.example.study.OrderService.domain.enums.PaymentType;
import com.example.study.OrderService.dto.OrderDTO;
import com.example.study.OrderService.service.OrderSelectionService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderSelectionService orderService;

    @Autowired
    public OrderController(OrderSelectionService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_REGULAR_USER"})
    @Transactional
    public ResponseEntity<?> getAllOrders() {
        List<?> orders = orderService.getAllOrders();

        if (orders.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("Заказы не найдены");
        }

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/by-city")
    @Secured({"ROLE_ADMIN", "ROLE_REGULAR_USER"})
    @Transactional
    public ResponseEntity<?> getOrdersByCity(@RequestParam String city) {
        List<?> filteredOrders =
                orderService.getOrdersByCustomerAddressCity(city);

        if (filteredOrders.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("Заказы по данному городу не найдены");
        }

        return ResponseEntity.ok(filteredOrders);
    }

    @GetMapping("/by-date-between")
    @Secured({"ROLE_ADMIN", "ROLE_REGULAR_USER"})
    @Transactional
    public ResponseEntity<?> getOrdersByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {

        if (start.isAfter(end)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Дата начала должна быть раньше даты окончания");
        }

        List<?> filteredOrders = orderService.getOrdersByDateBetween(start, end);

        if (filteredOrders.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("Заказы по данному промежутку времени не найдены");
        }

        return ResponseEntity.ok(filteredOrders);
    }

    @GetMapping("/by-payment-type")
    @Secured({"ROLE_ADMIN", "ROLE_REGULAR_USER"})
    @Transactional
    public ResponseEntity<?> getOrdersByPaymentType(@RequestParam String paymentType) {
        try {
            List<?> filteredOrders = orderService.getOrdersByPaymentType(PaymentType.valueOf(paymentType));

            if (filteredOrders.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body("Заказы по данному типу платежа не найдены");
            }

            return ResponseEntity.ok(filteredOrders);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Указан неверный тип платежа");
        }
    }

    @GetMapping("/by-customer-name")
    @Secured({"ROLE_ADMIN", "ROLE_REGULAR_USER"})
    @Transactional
    public ResponseEntity<?> getOrdersByCustomerName(@RequestParam String customerName) {
        List<?> filteredOrders = orderService.getOrdersByCustomerName(customerName);

        if (filteredOrders.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Заказы на данное имя не найдены");
        }

        return ResponseEntity.ok(filteredOrders);
    }

    @GetMapping("/by-payment-status")
    @Secured({"ROLE_ADMIN", "ROLE_REGULAR_USER"})
    @Transactional
    public ResponseEntity<?> getOrdersByPaymentStatus(@RequestParam String paymentStatus) {
        try {
            List<?> filteredOrders = orderService.getOrdersByPaymentStatus(
                    PaymentStatus.valueOf(paymentStatus));

            if (filteredOrders.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body("Заказы по данному статусу платежа не найдены");
            }

            return ResponseEntity.ok(filteredOrders);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Указан неверный статус платежа");
        }
    }

    @GetMapping("/by-order-status")
    @Secured({"ROLE_ADMIN", "ROLE_REGULAR_USER"})
    @Transactional
    public ResponseEntity<?> getOrdersByOrderStatus(@RequestParam String orderStatus) {
        try {
            List<?> filteredOrders = orderService.getOrdersByOrderStatus(
                    OrderStatus.valueOf(orderStatus));

            if (filteredOrders.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body("Заказы с данным статусом заказа не найдены.");
            }

            return ResponseEntity.ok(filteredOrders);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Указан неверный статус заказа");
        }
    }
}
