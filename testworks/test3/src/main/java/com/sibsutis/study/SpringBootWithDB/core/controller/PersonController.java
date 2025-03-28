package com.sibsutis.study.SpringBootWithDB.core.controller;

import com.sibsutis.study.SpringBootWithDB.core.dto.PersonDTO;
import com.sibsutis.study.SpringBootWithDB.core.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService service;

    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        List<PersonDTO> personsDTOS = service.getAllPersons();
        return ResponseEntity.ok(personsDTOS);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> addPerson(@RequestBody PersonDTO personDTO) {
        PersonDTO addedPersonDTO = service.addPerson(personDTO);
        return ResponseEntity.status(202).body(addedPersonDTO);
    }
}
