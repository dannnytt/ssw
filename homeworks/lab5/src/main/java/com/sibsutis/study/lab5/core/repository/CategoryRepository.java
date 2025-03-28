package com.sibsutis.study.lab5.core.repository;

import com.sibsutis.study.lab5.core.model.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByName(String name);
}
