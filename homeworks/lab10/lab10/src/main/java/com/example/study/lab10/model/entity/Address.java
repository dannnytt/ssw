package com.example.study.lab10.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Column(name = "address_city")
    private String city;

    @Column(name = "address_street")
    private String street;

    @Column(name = "address_zipcode")
    private String zipcode;
}