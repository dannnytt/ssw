package com.sibsutis.study.lab4.core.controller;

import com.sibsutis.study.lab4.core.model.Pet;
import com.sibsutis.study.lab4.core.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PetControllerTest {

    @Mock
    private PetService petService;

    @InjectMocks
    private PetController petController;

    private Pet pet;

    @BeforeEach
    void setUp() {
        pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");
    }

    @Test
    void getPetById_whenPetExists_shouldReturnPet() {
        when(petService.getPetById(1L)).thenReturn(Optional.of(pet));

        ResponseEntity<Pet> response = petController.getPetById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(pet, response.getBody());
    }


    @Test
    void addPet_shouldReturnAddedPet() {
        when(petService.addPet(pet)).thenReturn(pet);

        ResponseEntity<Pet> response = petController.addPet(pet);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(pet, response.getBody());
    }

    @Test
    void updatePet_whenPetExists_shouldReturnUpdatedPet() {
        when(petService.updatePet(1L, pet)).thenReturn(Optional.of(pet));

        ResponseEntity<Pet> response = petController.updatePet(1L, pet);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(pet, response.getBody());
    }

    @Test
    void deletePet_whenPetExists_shouldReturnNoContent() {
        when(petService.deletePet(1L)).thenReturn(true);

        ResponseEntity<Void> response = petController.deletePet(1L);

        assertEquals(204, response.getStatusCode().value());
    }
}