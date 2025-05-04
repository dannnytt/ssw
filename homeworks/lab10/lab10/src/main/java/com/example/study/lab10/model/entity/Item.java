package com.example.study.lab10.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.study.lab10.model.measurement.Weight;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "measurement.name", column = @Column(name = "measurement_name")),
            @AttributeOverride(name = "measurement.symbol", column = @Column(name = "measurement_symbol"))
    })
    private Weight shippingWeight;

    private String description;
}
