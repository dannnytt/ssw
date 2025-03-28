package com.sibsutis.study.lab5.core.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class PetDTO {
    private Integer id;
    private String name;
    private String categoryName;
    private List<String> tagNames;
    private String status;

    public PetDTO() {}

    public PetDTO(String name,
                  String categoryName,
                  List<String> tagNames,
                  String status) {

        this.name = name;
        this.categoryName = categoryName;
        this.tagNames = tagNames;
        this.status = status;
    }

    public PetDTO(Integer id,
                  String name,
                  String categoryName,
                  List<String> tagNames,
                  String status) {

        this.id = id;
        this.name = name;
        this.categoryName = categoryName;
        this.tagNames = tagNames;
        this.status = status;
    }
}
