package com.sibsutis.study.lab5.core.repository;

import com.sibsutis.study.lab5.core.model.entity.PetEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends CrudRepository<PetEntity, Integer> {
}
