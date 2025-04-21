package com.sibsutis.study.lab7.domain.entity.item;

import com.sibsutis.study.lab7.domain.entity.order.OrderDetail;
import com.sibsutis.study.lab7.domain.value.measurements.Weight;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items")
public class Item {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "shipping_weight_name")),
            @AttributeOverride(name = "symbol", column = @Column(name = "shipping_weight_symbol")),
            @AttributeOverride(name = "value", column = @Column(name = "shipping_weight_value"))
    })
    private Weight shippingWeight;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;
}
