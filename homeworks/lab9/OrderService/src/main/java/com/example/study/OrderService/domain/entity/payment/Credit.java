package com.example.study.OrderService.domain.entity.payment;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@DiscriminatorValue("CREDIT")
public class Credit extends Payment {
    private String number;
    private String type;
    private LocalDateTime expDate;

    public Credit() {}

    public Credit(String number, String type, LocalDateTime expDate) {
        this.number = number;
        this.type = type;
        this.expDate = expDate;
    }

    public Credit(Float amount, String number, String type, LocalDateTime expDate) {
        super(amount);
        this.number = number;
        this.type = type;
        this.expDate = expDate;
    }
}
