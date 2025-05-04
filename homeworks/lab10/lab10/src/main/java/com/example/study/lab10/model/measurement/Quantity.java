package com.example.study.lab10.model.measurement;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Quantity {
    @Embedded
    private Measurement measurement;

    @Column(name = "quantity_value")
    private Integer value;
}
