package com.example.study.lab10.model.measurement;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Measurement {
    @Column(name = "measurement_name")
    private String name;
    @Column(name = "measurement_symbol")
    private String symbol;
}
