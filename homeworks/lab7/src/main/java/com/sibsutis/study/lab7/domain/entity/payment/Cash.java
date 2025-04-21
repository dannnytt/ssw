package com.sibsutis.study.lab7.domain.entity.payment;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("CASH")
public class Cash extends Payment {
    private Float cashTendered;

    public Cash() {}

    public Cash(Float cashTendered) {
        this.cashTendered = cashTendered;
    }

    public Cash(Float amount, Float cashTendered) {
        super(amount);
        this.cashTendered = cashTendered;
    }
}
