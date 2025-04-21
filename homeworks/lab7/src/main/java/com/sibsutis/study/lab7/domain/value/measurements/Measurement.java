package com.sibsutis.study.lab7.domain.value.measurements;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class Measurement {

    @Column(name = "name")
    protected String name;

    @Column(name = "symbol")
    protected String symbol;

    public Measurement() {}

    public Measurement(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }
}
