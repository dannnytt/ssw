package com.sibsutis.study.lab5.core.service;

import com.sibsutis.study.lab5.core.config.TestContainerConfig;
import com.sibsutis.study.lab5.core.model.dto.PetDTO;
import com.sibsutis.study.lab5.core.model.entity.CategoryEntity;
import com.sibsutis.study.lab5.core.model.entity.PetEntity;
import com.sibsutis.study.lab5.core.model.entity.TagEntity;
import com.sibsutis.study.lab5.core.model.enums.Status;
import com.sibsutis.study.lab5.core.repository.CategoryRepository;
import com.sibsutis.study.lab5.core.repository.PetRepository;
import com.sibsutis.study.lab5.core.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

@SpringBootTest(classes = TestContainerConfig.class)
@Transactional
public class PetServiceIT {

    @Autowired
    private PetServiceImpl petService;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    private Integer createdPetId;

    @BeforeEach
    void setUp() {
        petRepository.deleteAll();
        tagRepository.deleteAll();
        categoryRepository.deleteAll();

        CategoryEntity category = categoryRepository.save(new CategoryEntity("Dog"));
        TagEntity tag1 = tagRepository.save(new TagEntity("Friendly"));
        TagEntity tag2 = tagRepository.save(new TagEntity("Energetic"));

        PetEntity pet = new PetEntity();
        pet.setName("Buddy");
        pet.setCategory(category);
        pet.setTags(List.of(tag1, tag2));
        pet.setStatus(Status.AVAILABLE);

        PetEntity savedPet = petRepository.save(pet);
        createdPetId = savedPet.getId();
    }

    @Test
    void shouldAddPet() {
        PetDTO newPet = new PetDTO();
        newPet.setName("Max");
        newPet.setCategoryName("Cat");
        newPet.setTagNames(List.of("Calm", "Cuddly"));
        newPet.setStatus("AVAILABLE");

        PetDTO savedPet = petService.addPet(newPet);

        assertThat(savedPet).isNotNull();
        assertThat(savedPet.getName()).isEqualTo("Max");
        assertThat(savedPet.getCategoryName()).isEqualTo("Cat");
        assertThat(savedPet.getTagNames()).containsExactlyInAnyOrder("Calm", "Cuddly");
        assertThat(savedPet.getStatus()).isEqualTo("AVAILABLE");

        assertThat(categoryRepository.findByName("Cat")).isPresent();
        assertThat(tagRepository.findByName("Calm")).isPresent();
        assertThat(tagRepository.findByName("Cuddly")).isPresent();
    }

    @Test
    void shouldGetAllPets() {
        List<PetDTO> pets = petService.getAllPets();

        assertThat(pets).hasSize(1);

        PetDTO pet = pets.get(0);
        assertThat(pet.getName()).isEqualTo("Buddy");
        assertThat(pet.getCategoryName()).isEqualTo("Dog");
        assertThat(pet.getTagNames()).containsExactlyInAnyOrder("Friendly", "Energetic");
        assertThat(pet.getStatus()).isEqualTo("AVAILABLE");
    }

    @Test
    void shouldGetPetById() {
        Optional<PetDTO> petOpt = petService.getPetById(createdPetId);

        assertThat(petOpt).isPresent();
        assertThat(petOpt.get()).satisfies(pet -> {
            assertThat(pet.getName()).isEqualTo("Buddy");
            assertThat(pet.getCategoryName()).isEqualTo("Dog");
            assertThat(pet.getTagNames()).containsExactlyInAnyOrder("Friendly", "Energetic");
            assertThat(pet.getStatus()).isEqualTo("AVAILABLE");
        });
    }

    @Test
    void shouldUpdatePet() {
        PetDTO updateData = new PetDTO();
        updateData.setName("Charlie");
        updateData.setCategoryName("Cat");
        updateData.setTagNames(List.of("Smart", "Loyal"));
        updateData.setStatus("SOLD");

        Optional<PetDTO> updatedPet = petService.updatePet(updateData, createdPetId);

        assertThat(updatedPet).isPresent();
        assertThat(updatedPet.get()).satisfies(pet -> {
            assertThat(pet.getName()).isEqualTo("Charlie");
            assertThat(pet.getCategoryName()).isEqualTo("Cat");
            assertThat(pet.getTagNames()).containsExactlyInAnyOrder("Smart", "Loyal");
            assertThat(pet.getStatus()).isEqualTo("SOLD");
        });

        assertThat(tagRepository.findByName("Friendly")).isPresent();
        assertThat(tagRepository.findByName("Energetic")).isPresent();
    }

    @Test
    void shouldDeletePetAndReturnTrue() {
        assertThat(petService.deletePetById(createdPetId)).isTrue();
        assertThat(petRepository.existsById(createdPetId)).isFalse();
    }

    @Test
    void shouldReturnFalseWhenDeletingNonExistentPet() {
        int nonExistentId = 9999;
        assertThat(petService.deletePetById(nonExistentId)).isFalse();
    }
}