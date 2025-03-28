package com.sibsutis.study.lab5.core.service;

import com.sibsutis.study.lab5.core.model.dto.PetDTO;
import java.util.List;
import java.util.Optional;

public interface PetService {

    public List<PetDTO> getAllPets();

    public Optional<PetDTO> getPetById(Integer id);

    public PetDTO addPet(PetDTO petDTO);

    public Optional<PetDTO> updatePet(PetDTO petDTO, Integer id);

    public Boolean deletePetById(Integer id);
}
