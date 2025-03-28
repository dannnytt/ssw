package com.sibsutis.study.lab5.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TestContainerConfig.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class PetControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Integer createdPetId;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        petRepository.deleteAll();
        categoryRepository.deleteAll();
        tagRepository.deleteAll();

        CategoryEntity category = new CategoryEntity("Dog");
        category = categoryRepository.save(category);

        TagEntity tag1 = new TagEntity("Friendly");
        TagEntity tag2 = new TagEntity("Energetic");
        tag1 = tagRepository.save(tag1);
        tag2 = tagRepository.save(tag2);
        List<TagEntity> tags = List.of(tag1, tag2);

        PetEntity pet = new PetEntity();
        pet.setName("Buddy");
        pet.setCategory(category);
        pet.setTags(tags);
        pet.setStatus(Status.AVAILABLE);

        PetEntity savedPet = petRepository.save(pet);
        createdPetId = savedPet.getId();
    }
    @Test
    void shouldCreatePet() throws Exception {
        PetDTO newPet = new PetDTO();
        newPet.setName("Max");
        newPet.setCategoryName("Cat");
        newPet.setTagNames(List.of("Calm", "Cuddly"));
        newPet.setStatus("AVAILABLE");

        mockMvc.perform(post("/api/v3/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPet)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Max"))
                .andExpect(jsonPath("$.categoryName").value("Cat"))
                .andExpect(jsonPath("$.tagNames").isArray())
                .andExpect(jsonPath("$.tagNames[0]").value("Calm"))
                .andExpect(jsonPath("$.tagNames[1]").value("Cuddly"))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));
    }

    @Test
    void shouldGetAllPets() throws Exception {
        mockMvc.perform(get("/api/v3/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[?(@.name == 'Buddy')]").exists());
    }

    @Test
    void shouldGetPetById() throws Exception {
        mockMvc.perform(get("/api/v3/pets/" + createdPetId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"))
                .andExpect(jsonPath("$.categoryName").value("Dog"))
                .andExpect(jsonPath("$.tagNames").isArray())
                .andExpect(jsonPath("$.tagNames[0]").value("Friendly"))
                .andExpect(jsonPath("$.tagNames[1]").value("Energetic"))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));
    }

    @Test
    @Transactional
    void shouldUpdatePet() throws Exception {
        TagEntity smartTag = new TagEntity("Smart");
        TagEntity loyalTag = new TagEntity("Loyal");
        tagRepository.saveAll(List.of(smartTag, loyalTag));

        mockMvc.perform(put("/api/v3/pets/" + createdPetId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "name": "Charlie",
                              "categoryName": "Dog",
                              "tagNames": ["Smart", "Loyal"],
                              "status": "SOLD"
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Charlie"))
                .andExpect(jsonPath("$.categoryName").value("Dog"))
                .andExpect(jsonPath("$.tagNames").isArray())
                .andExpect(jsonPath("$.tagNames", containsInAnyOrder("Smart", "Loyal")))
                .andExpect(jsonPath("$.status").value("SOLD"));

        mockMvc.perform(get("/api/v3/pets/" + createdPetId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Charlie"))
                .andExpect(jsonPath("$.tagNames", containsInAnyOrder("Smart", "Loyal")));
    }

    @Test
    void shouldDeletePet() throws Exception {
        mockMvc.perform(get("/api/v3/pets/" + createdPetId))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/v3/pets/" + createdPetId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v3/pets/" + createdPetId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentPet() throws Exception {
        int nonExistentId = 9999;
        mockMvc.perform(delete("/api/v3/pets/" + nonExistentId))
                .andExpect(status().isNotFound());
    }
}