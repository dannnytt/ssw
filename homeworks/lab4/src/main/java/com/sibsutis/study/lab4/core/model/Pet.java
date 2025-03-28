package com.sibsutis.study.lab4.core.model;

import com.sibsutis.study.lab4.core.model.enums.Status;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

    private Long id;
    private String name;
    private Category category;
    private List<Tag> tags;
    private Status status;
}
