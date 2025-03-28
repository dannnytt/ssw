package com.sibsutis.study.test3.repository;

import com.sibsutis.study.test3.model.entity.Institute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituteRepository extends CrudRepository<Institute, Integer> {
}
