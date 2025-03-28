package com.sibsutis.study.SpringBootWithDB.core.service;

import com.sibsutis.study.SpringBootWithDB.core.dto.PersonDTO;
import com.sibsutis.study.SpringBootWithDB.core.model.Person;
import com.sibsutis.study.SpringBootWithDB.core.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PersonService {

    private PersonRepository repository;

    @Autowired
    public  PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<PersonDTO> getAllPersons() {
        Iterable<Person> persons = repository.findAll();

        return StreamSupport.stream(persons.spliterator(), false)
                .map(person -> new PersonDTO(person))
                .collect(Collectors.toList());
    }

    public PersonDTO addPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());
        person.setAge(personDTO.getAge());

        Person savedPerson = repository.save(person);

        return new PersonDTO((savedPerson));
    }
}
