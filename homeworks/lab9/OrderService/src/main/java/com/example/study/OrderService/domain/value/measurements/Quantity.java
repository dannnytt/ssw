package com.example.study.OrderService.domain.value.measurements;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class Quantity extends Measurement {

    private Integer value;

    public Quantity() {}

    public Quantity(Integer value) {
        this.value = value;
    }

    public Quantity(String name, String symbol, Integer value) {
        super(name, symbol);
        this.value = value;
    }
}
