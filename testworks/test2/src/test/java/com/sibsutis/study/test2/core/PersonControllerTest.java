package com.sibsutis.study.test2.core;

import com.sibsutis.study.test2.core.controller.PersonController;
import com.sibsutis.study.test2.core.model.Person;
import com.sibsutis.study.test2.core.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @Mock
    private PersonService service;

    @InjectMocks
    private PersonController controller;

    private Person person;

    @BeforeEach
    void setup() {
        person = new Person();
        person.setId(1L);
        person.setName("Daniel");
        person.setAge(20L);
    }

    @Test
    public void getAllPersons() {
        when(service.getAllPersons()).thenReturn(List.of(person));

        ResponseEntity<List<Person>> response = controller.getAllPersons();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1L, response.getBody().size());
        assertEquals("Daniel", response.getBody().get(0).getName());
        assertEquals(20L, response.getBody().get(0).getAge());
    }

    @Test
    public void addPerson() {
        when(service.addPerson(person)).thenReturn(person);

        ResponseEntity<Person> response = controller.addPerson(person);
        assertEquals(202, response.getStatusCode().value());
        assertEquals(person, response.getBody());

    }

}
