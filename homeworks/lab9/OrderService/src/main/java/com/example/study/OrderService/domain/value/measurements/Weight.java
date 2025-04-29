package com.example.study.OrderService.domain.value.measurements;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Embeddable
@Getter @Setter
public class Weight extends Measurement{

    private BigDecimal value;

    public Weight() {}

    public Weight(BigDecimal value) {
        this.value = value;
    }

    public Weight(String name, String symbol, BigDecimal value) {
        super(name, symbol);
        this.value = value;
    }
}
