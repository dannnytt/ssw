package com.sibsutis.study.lab5.core.controller;

import com.sibsutis.study.lab5.core.model.dto.PetDTO;
import com.sibsutis.study.lab5.core.service.PetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v3/pets")
public class PetController {

    private PetServiceImpl petService;

    @Autowired
    public PetController(PetServiceImpl petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> getAllPets() {
        List<PetDTO> petsDTOs = petService.getAllPets();

        if (petsDTOs.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        return ResponseEntity.status(HttpStatus.OK).body(petsDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getPetById(@PathVariable Integer id) {
        return petService.getPetById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<PetDTO> addPet(@RequestBody PetDTO petDTO) {
        PetDTO addedPet = petService.addPet(petDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedPet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDTO> updatePet(@RequestBody PetDTO petDTO, @PathVariable Integer id) {
        return petService.updatePet(petDTO, id)
                .map(pet -> ResponseEntity.status(HttpStatus.OK).body(pet))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetById(@PathVariable Integer id) {
        return petService.deletePetById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
