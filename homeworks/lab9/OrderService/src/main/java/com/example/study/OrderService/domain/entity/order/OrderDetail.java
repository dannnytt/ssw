package com.example.study.OrderService.domain.entity.order;

import com.example.study.OrderService.domain.entity.item.Item;
import com.example.study.OrderService.domain.enums.TaxStatus;
import com.example.study.OrderService.domain.value.measurements.Quantity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "quantity_name")),
            @AttributeOverride(name = "symbol", column = @Column(name = "quantity_symbol")),
            @AttributeOverride(name = "value", column = @Column(name = "quantity_value"))
    })
    private Quantity quantity;

    @Column(name = "tax_status")
    @Enumerated(EnumType.STRING)
    private TaxStatus taxStatus;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Item item;
}
