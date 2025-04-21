package com.sibsutis.study.lab7.domain.entity.payment;

import com.sibsutis.study.lab7.domain.entity.order.Order;
import com.sibsutis.study.lab7.domain.enums.PaymentStatus;
import com.sibsutis.study.lab7.domain.enums.PaymentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "payment_type")
public abstract class Payment {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "amount")
    private Float amount;

    @Column(
            name = "payment_type",
            insertable = false,
            updatable = false
    )
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Payment() {}

    public Payment(Float amount) {
        this.amount = amount;
    }
}
