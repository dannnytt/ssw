package com.sibsutis.study.test2.core.service;

import com.sibsutis.study.test2.core.model.Person;
import com.sibsutis.study.test2.core.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private PersonRepository repository;

    @Autowired
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<Person> getAllPersons() {
        return repository.getAllPersons();
    }

    public Person addPerson(Person person) {
        return repository.addPerson(person);
    }


}
