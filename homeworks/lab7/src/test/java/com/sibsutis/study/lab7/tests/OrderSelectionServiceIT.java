package com.sibsutis.study.lab7.tests;

import com.sibsutis.study.lab7.config.TestContainersConfig;
import com.sibsutis.study.lab7.domain.entity.customer.Customer;
import com.sibsutis.study.lab7.domain.entity.item.Item;
import com.sibsutis.study.lab7.domain.entity.order.Order;
import com.sibsutis.study.lab7.domain.entity.order.OrderDetail;
import com.sibsutis.study.lab7.domain.entity.payment.Cash;
import com.sibsutis.study.lab7.domain.entity.payment.Check;
import com.sibsutis.study.lab7.domain.entity.payment.Credit;
import com.sibsutis.study.lab7.domain.enums.OrderStatus;
import com.sibsutis.study.lab7.domain.enums.PaymentStatus;
import com.sibsutis.study.lab7.domain.enums.PaymentType;
import com.sibsutis.study.lab7.domain.enums.TaxStatus;
import com.sibsutis.study.lab7.domain.value.address.Address;
import com.sibsutis.study.lab7.domain.value.measurements.Quantity;
import com.sibsutis.study.lab7.domain.value.measurements.Weight;
import com.sibsutis.study.lab7.repository.CustomerRepository;
import com.sibsutis.study.lab7.repository.ItemRepository;
import com.sibsutis.study.lab7.repository.OrderRepository;
import com.sibsutis.study.lab7.service.OrderSelectionService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest(classes = TestContainersConfig.class)
public class OrderSelectionServiceIT {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderSelectionService service;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
        orderRepository.deleteAll();
        itemRepository.deleteAll();

        Customer customer1 = Customer.builder()
                .name("Customer1")
                .address(new Address(
                        "Novosibirsk",
                        "Borisa Bogatkova street",
                        "12345"
                ))
                .build();

        Customer customer2 = Customer.builder()
                .name("Customer2")
                .address(new Address(
                        "Novosibirsk",
                        "Krasniy prospect",
                        "54321"
                ))
                .build();

        Customer customer3 = Customer.builder()
                .name("Customer3")
                .address(new Address(
                        "Gorno-Altaysk",
                        "Lenina street",
                        "11111"
                ))
                .build();

        customerRepository.saveAll(List.of(customer1, customer2, customer3));

        Order order1 = Order.builder()
                .customer(customer1)
                .date(LocalDateTime.of(2025, 4, 21, 15, 40))
                .orderStatus(OrderStatus.PENDING)
                .build();

        Order order2 = Order.builder()
                .customer(customer2)
                .date(LocalDateTime.of(2025, 4, 10, 21, 30))
                .orderStatus(OrderStatus.PAID)
                .build();

        Order order3 = Order.builder()
                .customer(customer3)
                .date(LocalDateTime.of(2025, 3, 1, 13, 45))
                .orderStatus(OrderStatus.CANCELLED)
                .build();

        orderRepository.saveAll(List.of(order1, order2, order3));

        Item smartphone = Item.builder()
                .description("Smartphone")
                .shippingWeight(new Weight(
                        "Kilogram",
                        "kg",
                        new BigDecimal("0.5")
                ))
                .build();

        Item laptop = Item.builder()
                .description("Laptop")
                .shippingWeight(new Weight(
                        "Kilogram",
                        "kg",
                        new BigDecimal("2.0")
                ))
                .build();

        itemRepository.saveAll(List.of(smartphone, laptop));

        OrderDetail orderDetail1 = OrderDetail.builder()
                .quantity(new Quantity("Piece", "pcs", 2))
                .taxStatus(TaxStatus.TAXABLE)
                .order(order1)
                .item(smartphone)
                .build();

        OrderDetail orderDetail2 = OrderDetail.builder()
                .quantity(new Quantity("Piece", "pcs", 1))
                .taxStatus(TaxStatus.NON_TAXABLE)
                .order(order2)
                .item(laptop)
                .build();

        OrderDetail orderDetail3 = OrderDetail.builder()
                .quantity(new Quantity("Piece", "pcs", 2))
                .taxStatus(TaxStatus.TAXABLE)
                .order(order3)
                .item(laptop)
                .build();

        order1.getOrderDetails().add(orderDetail1);
        order2.getOrderDetails().add(orderDetail2);
        order3.getOrderDetails().add(orderDetail3);



        Credit creditPayment = new Credit(
                15000f,
                "1234-5678-9101-1121",
                "VISA",
                LocalDateTime.of(2025, 12, 31, 0, 0)
        );
        creditPayment.setPaymentStatus(PaymentStatus.COMPLETED);
        creditPayment.setOrder(order1);

        Cash cashPayment = new Cash(5000f, 5000f);
        cashPayment.setPaymentStatus(PaymentStatus.PENDING);
        cashPayment.setOrder(order2);

        Check checkPayment = new Check("Customer3", "Sberbank");
        checkPayment.setPaymentStatus(PaymentStatus.CANCELLED);
        checkPayment.setOrder(order3);

        order1.getPayments().add(creditPayment);
        order2.getPayments().add(cashPayment);
        order3.getPayments().add(checkPayment);

        orderRepository.saveAll(List.of(order1, order2, order3));

    }

    @Test
    public void testFindOrdersByCustomerCity() {
        List<?> orders1 = service.getOrdersByCustomerAddressCity("Novosibirsk");
        List<?> orders2 = service.getOrdersByCustomerAddressCity("Gorno-Altaysk");
        Assertions.assertEquals(2, orders1.size());
        Assertions.assertEquals(1, orders2.size());
    }

    @Test
    public void testFindOrdersByDateBetween() {
        LocalDateTime from = LocalDateTime.of(2025, 4, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2025, 4, 30, 23, 59);

        List<?> orders = service.getOrdersByDateBetween(from, to);
        Assertions.assertEquals(2, orders.size());
    }

    @Test
    public void testFindOrdersByPaymentType() {
        List<?> cashOrders = service.getOrdersByPaymentType(PaymentType.CASH);
        Assertions.assertEquals(1, cashOrders.size());

        List<?> checkOrders = service.getOrdersByPaymentType(PaymentType.CHECK);
        Assertions.assertEquals(1, checkOrders.size());

        List<?> creditOrders = service.getOrdersByPaymentType(PaymentType.CREDIT);
        Assertions.assertEquals(1, creditOrders.size());
    }

    @Test
    public void testFindOrdersByCustomerName() {
        List<?> orders1 = service.getOrdersByCustomerName("Customer1");
        Assertions.assertEquals(1, orders1.size());

        List<?> orders2 = service.getOrdersByCustomerName("Customer2");
        Assertions.assertEquals(1, orders2.size());

        List<?> orders3 = service.getOrdersByCustomerName("Customer3");
        Assertions.assertEquals(1, orders3.size());
    }

    @Test
    public void testFindOrdersByPaymentStatus() {
        List<?> completedOrders = service.getOrdersByPaymentStatus(PaymentStatus.COMPLETED);
        Assertions.assertEquals(1, completedOrders.size());

        List<?> pendingOrders = service.getOrdersByPaymentStatus(PaymentStatus.PENDING);
        Assertions.assertEquals(1, pendingOrders.size());

        List<?> cancelledOrders = service.getOrdersByPaymentStatus(PaymentStatus.CANCELLED);
        Assertions.assertEquals(1, cancelledOrders.size());
    }

    @Test
    public void testFindOrdersByOrderStatus() {
        List<?> pendingOrders = service.getOrdersByOrderStatus(OrderStatus.PENDING);
        Assertions.assertEquals(1, pendingOrders.size());

        List<Order> paidOrders = service.getOrdersByOrderStatus(OrderStatus.PAID);
        Assertions.assertEquals(1, paidOrders.size());

        List<?> canceledOrders = service.getOrdersByOrderStatus(OrderStatus.CANCELLED);
        Assertions.assertEquals(1, canceledOrders.size());
    }
}
