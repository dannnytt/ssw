package com.sibsutis.study.test3.repository;

import com.sibsutis.study.test3.model.entity.University;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends CrudRepository<University, Integer> {
}
