package com.sibsutis.study.lab4.core.service;

import com.sibsutis.study.lab4.core.model.Pet;
import com.sibsutis.study.lab4.core.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    private Pet pet;

    @BeforeEach
    void setUp() {
        pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");
    }

    @Test
    void getPetById_whenPetExists_shouldReturnPet() {
        when(petRepository.getPetById(1L)).thenReturn(Optional.of(pet));

        Optional<Pet> foundPet = petService.getPetById(1L);

        assertTrue(foundPet.isPresent());
        assertEquals(pet, foundPet.get());
    }

    @Test
    void getPetById_whenPetDoesNotExist_shouldReturnEmpty() {
        when(petRepository.getPetById(1L)).thenReturn(Optional.empty());

        Optional<Pet> foundPet = petService.getPetById(1L);

        assertFalse(foundPet.isPresent());
    }

    @Test
    void addPet_shouldReturnAddedPet() {
        when(petRepository.addPet(pet)).thenReturn(pet);

        Pet addedPet = petService.addPet(pet);

        assertEquals(pet, addedPet);
    }

    @Test
    void updatePet_whenPetExists_shouldReturnUpdatedPet() {
        when(petRepository.updatePet(1L, pet)).thenReturn(Optional.of(pet));

        Optional<Pet> updatedPet = petService.updatePet(1L, pet);

        assertTrue(updatedPet.isPresent());
        assertEquals(pet, updatedPet.get());
    }

    @Test
    void updatePet_whenPetDoesNotExist_shouldReturnEmpty() {
        when(petRepository.updatePet(1L, pet)).thenReturn(Optional.empty());

        Optional<Pet> updatedPet = petService.updatePet(1L, pet);

        assertFalse(updatedPet.isPresent());
    }

    @Test
    void deletePet_whenPetExists_shouldReturnTrue() {
        when(petRepository.deletePet(1L)).thenReturn(true);

        boolean isDeleted = petService.deletePet(1L);

        assertTrue(isDeleted);
    }

    @Test
    void deletePet_whenPetDoesNotExist_shouldReturnFalse() {
        when(petRepository.deletePet(1L)).thenReturn(false);

        boolean isDeleted = petService.deletePet(1L);

        assertFalse(isDeleted);
    }
}
