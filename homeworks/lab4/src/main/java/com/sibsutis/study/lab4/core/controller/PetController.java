package com.sibsutis.study.lab4.core.controller;

import com.sibsutis.study.lab4.core.model.Pet;
import com.sibsutis.study.lab4.core.service.PetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/{petId}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long petId) {
        return petService.getPetById(petId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).build());
    }

    @PostMapping
    public ResponseEntity<Pet> addPet(@RequestBody Pet pet) {
        Pet addedPet = petService.addPet(pet);
        return ResponseEntity.ok(addedPet);
    }

    @PutMapping("/{petId}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long petId, @RequestBody Pet pet) {
        return petService.updatePet(petId, pet)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).build());
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable Long petId) {
        return petService.deletePet(petId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(404).build();
    }
}