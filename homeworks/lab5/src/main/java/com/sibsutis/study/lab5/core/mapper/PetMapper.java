package com.sibsutis.study.lab5.core.mapper;

import com.sibsutis.study.lab5.core.model.enums.Status;
import com.sibsutis.study.lab5.core.model.dto.PetDTO;
import com.sibsutis.study.lab5.core.model.entity.CategoryEntity;
import com.sibsutis.study.lab5.core.model.entity.PetEntity;
import com.sibsutis.study.lab5.core.model.entity.TagEntity;
import com.sibsutis.study.lab5.core.model.model.CategoryModel;
import com.sibsutis.study.lab5.core.model.model.PetModel;
import com.sibsutis.study.lab5.core.model.model.TagModel;

import java.util.stream.Collectors;


public class PetMapper {

    // Entity -> Model
    public static PetModel toModel(PetEntity petEntity) {
        return new PetModel(
                petEntity.getName(),

                new CategoryModel(
                        petEntity.getCategory().getName()
                ),

                petEntity.getTags().stream()
                        .map(tag -> new TagModel(tag.getName()))
                        .collect(Collectors.toList()),

                petEntity.getStatus()
        );
    }

    // Model -> Entity
    public static PetEntity toEntity(PetModel petModel) {
        return new PetEntity(
                petModel.getName(),
                new CategoryEntity(
                        petModel.getCategory().getName()
                ),

                petModel.getTags().stream()
                        .map(tag -> new TagEntity(tag.getName()))
                        .collect(Collectors.toList()),

                petModel.getStatus()
        );
    }

    // Model -> DTO
    public static PetDTO toDTO(PetModel petModel) {
        return new PetDTO(
                petModel.getName(),
                petModel.getCategory().getName(),
                petModel.getTags().stream()
                        .map(TagModel::getName)
                        .toList(),
                petModel.getStatus().name()

        );
    }

    // DTO -> Model
    public static PetModel toModel(PetDTO petDTO) {
        return new PetModel(
                petDTO.getName(),
                new CategoryModel(petDTO.getCategoryName()),
                petDTO.getTagNames().stream()
                        .map(TagModel::new)
                        .toList(),
                Status.valueOf(petDTO.getStatus().toUpperCase())
        );
    }
}
