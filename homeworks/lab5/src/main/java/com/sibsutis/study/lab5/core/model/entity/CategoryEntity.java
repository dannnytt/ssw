package com.sibsutis.study.lab5.core.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categories", schema = "public")
@Getter
@Setter
public class CategoryEntity {

    @Id
    @Column(name  = "category_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_name", nullable = false)
    private String name;

    public CategoryEntity() {}
    public CategoryEntity(String name) {
        this.name = name;
    }
}
