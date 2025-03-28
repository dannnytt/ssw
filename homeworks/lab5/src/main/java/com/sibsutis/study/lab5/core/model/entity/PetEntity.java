package com.sibsutis.study.lab5.core.model.entity;

import com.sibsutis.study.lab5.core.model.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "pets", schema = "public")
@Getter
@Setter
public class PetEntity {

    @Id
    @Column(name = "pet_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pet_name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "pets_tags",
            joinColumns = @JoinColumn(name = "pet_id", referencedColumnName = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tag_id")
    )
    private List<TagEntity> tags;

    @Enumerated(EnumType.STRING)
    @Column(name = "pet_status", nullable = false)
    private Status status;


    public PetEntity() {}

    public PetEntity(String name,
                     CategoryEntity category,
                     List<TagEntity> tags,
                     Status status) {

        this.name = name;
        this.category = category;
        this.tags = tags;
        this.status = status;
    }
}
