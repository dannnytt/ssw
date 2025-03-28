package com.sibsutis.study.lab5.core.model.model;

import com.sibsutis.study.lab5.core.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetModel {
    private String name;
    private CategoryModel category;
    private List<TagModel> tags;
    private Status status;
}
