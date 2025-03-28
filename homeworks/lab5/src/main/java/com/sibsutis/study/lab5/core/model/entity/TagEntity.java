package com.sibsutis.study.lab5.core.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tags", schema = "public")
@Getter
@Setter
public class TagEntity {

    @Id
    @Column(name = "tag_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tag_name", nullable = false)
    private String name;

    public TagEntity() {}

    public TagEntity(String name) {
        this.name = name;
    }
}
