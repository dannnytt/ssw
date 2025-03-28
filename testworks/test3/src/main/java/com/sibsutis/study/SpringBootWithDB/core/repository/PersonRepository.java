package com.sibsutis.study.SpringBootWithDB.core.repository;

import com.sibsutis.study.SpringBootWithDB.core.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}
