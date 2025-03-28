package com.sibsutis.study.test2.core.repository;

import com.sibsutis.study.test2.core.model.Person;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PersonRepository {

    private Map<Long, Person> repository = new HashMap<>();

    public List<Person> getAllPersons() {
        return new ArrayList<>(repository.values());
    }

    public Person addPerson(Person person) {
        person.setId(generateId());
        repository.put(person.getId(), person);
        return  person;
    }

    private Long generateId() {
        return (long) (repository.size() + 1);
    }
}
