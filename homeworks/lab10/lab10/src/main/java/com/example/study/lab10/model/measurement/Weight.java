package com.example.study.lab10.model.measurement;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Weight {
    @Embedded
    private Measurement measurement;

    @Column(name = "shipping_weight_value")
    private BigDecimal value;
}
