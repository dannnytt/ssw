package com.sibsutis.study.lab5.core.service;

import com.sibsutis.study.lab5.core.model.enums.Status;
import com.sibsutis.study.lab5.core.model.dto.PetDTO;
import com.sibsutis.study.lab5.core.mapper.PetMapper;
import com.sibsutis.study.lab5.core.model.entity.CategoryEntity;
import com.sibsutis.study.lab5.core.model.entity.PetEntity;
import com.sibsutis.study.lab5.core.model.entity.TagEntity;
import com.sibsutis.study.lab5.core.model.model.PetModel;
import com.sibsutis.study.lab5.core.repository.CategoryRepository;
import com.sibsutis.study.lab5.core.repository.PetRepository;
import com.sibsutis.study.lab5.core.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository,
                          CategoryRepository categoryRepository,
                          TagRepository tagRepository) {

        this.petRepository = petRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<PetDTO> getAllPets() {
        Iterable<PetEntity> petsEntities = petRepository.findAll();

        List<PetModel> petModels = StreamSupport
                .stream(petsEntities.spliterator(), false)
                .map(PetMapper::toModel)
                .toList();

        return  petModels.stream()
                .map(PetMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<PetDTO> getPetById(Integer id) {
        Optional<PetEntity> petEntity = petRepository.findById(id);

        Optional<PetModel> petModel = petEntity.map(PetMapper::toModel);

        return petModel.map(PetMapper::toDTO);
    }

    @Override
    public PetDTO addPet(PetDTO petDTO) {
        CategoryEntity category = categoryRepository.findByName(petDTO.getCategoryName())
                .orElseGet(() -> categoryRepository.save(new CategoryEntity(petDTO.getCategoryName())));

        List<TagEntity> tags = petDTO.getTagNames().stream()
                .map(tagName ->
                        tagRepository.findByName(tagName)
                        .orElseGet(() -> tagRepository.save(new TagEntity(tagName)))
                )
                .collect(Collectors.toList());

        PetEntity petEntity = new PetEntity(
                petDTO.getName(),
                category,
                tags,
                Status.valueOf(petDTO.getStatus().toUpperCase())
        );

        PetEntity savedPetEntity = petRepository.save(petEntity);
        return PetMapper.toDTO(PetMapper.toModel(savedPetEntity));
    }

    @Override
    public Optional<PetDTO> updatePet(PetDTO petDTO, Integer id) {
        return petRepository.findById(id).map(pet ->{
            pet.setName(petDTO.getName());

            CategoryEntity category = categoryRepository.findByName(petDTO.getCategoryName())
                    .orElseGet(() -> categoryRepository.save(new CategoryEntity(petDTO.getCategoryName())));

            List<TagEntity> tags = petDTO.getTagNames().stream()
                    .map(tagName ->
                            tagRepository.findByName(tagName)
                                    .orElseGet(() -> tagRepository.save(new TagEntity(tagName)))
                    )
                    .collect(Collectors.toList());

            pet.setCategory(category);
            pet.setTags(tags);
            pet.setStatus(Status.valueOf(petDTO.getStatus().toUpperCase()));

            PetEntity updatedPet = petRepository.save(pet);
            return PetMapper.toDTO(PetMapper.toModel(updatedPet));
        });
    }

    @Override
    public Boolean deletePetById(Integer id) {
        if (petRepository.existsById(id)) {
            petRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
