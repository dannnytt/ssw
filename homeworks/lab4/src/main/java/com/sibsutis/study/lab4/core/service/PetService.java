package com.sibsutis.study.lab4.core.service;

import com.sibsutis.study.lab4.core.model.Pet;
import com.sibsutis.study.lab4.core.repository.PetRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public Optional<Pet> getPetById(Long petId) {
        return petRepository.getPetById(petId);
    }

    public Pet addPet(Pet pet) {
        return petRepository.addPet(pet);
    }

    public Optional<Pet> updatePet(Long petId, Pet pet) {
        return petRepository.updatePet(petId, pet);
    }

    public boolean deletePet(Long petId) {
        return petRepository.deletePet(petId);
    }
}
