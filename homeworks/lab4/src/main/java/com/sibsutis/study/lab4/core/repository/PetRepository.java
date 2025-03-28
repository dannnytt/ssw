package com.sibsutis.study.lab4.core.repository;

import com.sibsutis.study.lab4.core.model.Pet;

import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class PetRepository {
    private final Map<Long, Pet> petStore = new HashMap<>();

    public Optional<Pet> getPetById(Long petId) {
        return Optional.ofNullable(petStore.get(petId));
    }

    public Pet addPet(Pet pet) {
        pet.setId(generateId());
        petStore.put(pet.getId(), pet);
        return pet;
    }

    public Optional<Pet> updatePet(Long petId, Pet pet) {
        if (petStore.containsKey(petId)) {
            pet.setId(petId);
            petStore.put(petId, pet);
            return Optional.of(pet);
        }
        return Optional.empty();
    }

    public boolean deletePet(Long petId) {
        return petStore.remove(petId) != null;
    }

    private Long generateId() {
        return (long) (petStore.size() + 1);
    }
}
