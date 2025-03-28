package com.sibsutis.study.lab5.core.repository;

import com.sibsutis.study.lab5.core.model.entity.TagEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends CrudRepository<TagEntity, Integer> {
    Optional<TagEntity> findByName(String name);
}
