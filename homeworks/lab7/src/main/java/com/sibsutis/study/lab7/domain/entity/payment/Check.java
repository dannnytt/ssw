package com.sibsutis.study.lab7.domain.entity.payment;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("CHECK")
public class Check extends Payment {
    private String name;
    private String bankId;

    public Check() {}

    public Check(String name, String bankId) {
        this.name = name;
        this.bankId = bankId;
    }

    public Check(Float amount, String name, String bankId) {
        super(amount);
        this.name = name;
        this.bankId = bankId;
    }
}
