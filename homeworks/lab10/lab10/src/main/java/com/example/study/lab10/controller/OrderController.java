package com.example.study.lab10.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.example.study.lab10.dto.OrderRequest;
import com.example.study.lab10.dto.OrderResponse;
import com.example.study.lab10.dto.SuccessResponse;
import com.example.study.lab10.service.KeycloakService;
import com.example.study.lab10.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final KeycloakService keycloakService;

    @PreAuthorize("hasRole('REGULAR_USER')")
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@AuthenticationPrincipal Jwt jwt,
                                                        @RequestBody OrderRequest orderRequestDto) {
        Long userId = keycloakService.getUserIdFromJwt(jwt);
        OrderResponse response = orderService.createOrder(userId, orderRequestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(@AuthenticationPrincipal Jwt jwt) {
        Long userId = keycloakService.getUserIdFromJwt(jwt);

        if (keycloakService.hasRole(jwt, "ADMIN")) {
            return ResponseEntity.ok(orderService.getAllOrders());
        }

        return ResponseEntity.ok(orderService.getAllOrders(userId));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<SuccessResponse> deleteOrder(@AuthenticationPrincipal Jwt jwt,
                                                          @PathVariable Long orderId) {
        Long userId = keycloakService.getUserIdFromJwt(jwt);

        if (keycloakService.hasRole(jwt, "ADMIN")) {
            return ResponseEntity.ok(orderService.deleteOrder(orderId));
        }

        return ResponseEntity.ok(orderService.deleteOrder(userId, orderId));
    }
}
