package com.sibsutis.study.test3.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "universities", schema = "public")
@Getter @Setter
public class University {

    @Id
    @Column(name = "university_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "university_name", nullable = false)
    private String name;

    @Column(name = "university_phone", nullable = false)
    private String phone;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Institute> institutes;
}
