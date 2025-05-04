package com.example.study.lab10.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.study.lab10.dto.OrderRequest;
import com.example.study.lab10.dto.OrderResponse;
import com.example.study.lab10.dto.SuccessResponse;
import com.example.study.lab10.mapper.OrderMapper;
import com.example.study.lab10.model.OrderSearchCriteria;
import com.example.study.lab10.model.entity.Customer;
import com.example.study.lab10.model.entity.Item;
import com.example.study.lab10.model.entity.Order;
import com.example.study.lab10.model.entity.OrderDetail;
import com.example.study.lab10.model.entity.payment.Payment;
import com.example.study.lab10.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final PaymentRepository paymentRepository;
    private final ItemRepository itemRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderResponse createOrder(Long userId, OrderRequest orderRequestDto) {
        // Найдем заказчика
        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        // Найдем платеж
        Payment payment = paymentRepository.findById(orderRequestDto.getPaymentId())
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        // Преобразуем DTO -> Order
        Order order = orderMapper.toEntity(orderRequestDto);
        order.setCustomer(customer);
        order.setPayment(payment);

        // Установим связь для orderDetails вручную (нужно, чтобы Hibernate сохранил их правильно)
        for (OrderDetail detail : order.getOrderDetails()) {
            detail.setOrder(order);

            Long itemId = detail.getItem().getId();
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new EntityNotFoundException("Item with id " + itemId + " not found"));

            detail.setItem(item);

            System.out.println("quantity name: " + detail.getQuantity().getMeasurement().getName());
        }

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        orders.forEach(order -> {
            order.getOrderDetails().forEach(detail -> {
                System.out.println("Name: " + detail.getQuantity().getMeasurement().getName());
            });
        });
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getAllOrders(Long customerId) {
        return orderRepository.findAllByCustomerId(customerId)
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SuccessResponse deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
        return new SuccessResponse(orderId, "Заказ успешно удален.");
    }

    @Transactional
    public SuccessResponse deleteOrder(Long customerId, Long orderId) {
        orderRepository.deleteByIdAndCustomerId(orderId, customerId);
        return new SuccessResponse(orderId, "Заказ успешно удален.");
    }

    public List<Order> findOrdersByCriteria(OrderSearchCriteria criteria) {
        return orderRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getAddress() != null) {

                // Сначала соединяем Order с Customer
                Join<Order, Customer> customerJoin = root.join("customer");

                // Доступ к полям Address напрямую (они в той же таблице)
                predicates.add(cb.equal(customerJoin.get("address").get("city"), criteria.getAddress().getCity()));
                predicates.add(cb.equal(customerJoin.get("address").get("street"), criteria.getAddress().getStreet()));
            }

            if (criteria.getFromDate() != null && criteria.getToDate() != null) {
                predicates.add(cb.between(root.get("date"), criteria.getFromDate(), criteria.getToDate()));
            }

            if (criteria.getPayment() != null) {
                Join<Order, Payment> paymentJoin = root.join("payment");
                predicates.add(cb.equal(paymentJoin.type(), criteria.getPayment()));
            }

            if (criteria.getCustomerName() != null) {
                Join<Order, Customer> customerJoin = root.join("customer");
                predicates.add(cb.equal(customerJoin.get("name"), criteria.getCustomerName()));
            }

            if (criteria.getPaymentStatus() != null) {
                Join<Order, Payment> paymentJoin = root.join("payment");
                predicates.add(cb.equal(paymentJoin.get("status"), criteria.getPaymentStatus()));
            }

            if (criteria.getOrderStatus() != null) {
                predicates.add(cb.equal(root.get("status"), criteria.getOrderStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}