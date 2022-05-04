package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.handler.Operation;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.services.PersonServices;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class PersonController {

    private final PersonServices persons;

    public PersonController(PersonServices persons) {
        this.persons = persons;
    }

    @GetMapping("/persons")
    public List<Person> findAll() {
        return persons.findAll();
    }

    @GetMapping("/room/{room}/people")
    public List<Person> findAllPeople(@PathVariable("room") String name) {
        return persons.findAllPeople(name);
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        return persons.findById(id)
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Person with id %d not found", id)));

    }

    @PutMapping("/persons")
    public ResponseEntity<Void> update(@Validated(Operation.OnUpdate.class)
                                           @RequestBody Person person) {
        this.persons.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (persons.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Person with id %d not found", id));
        }
        this.persons.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/persons")
    public ResponseEntity<Person> patch(@Validated(Operation.OnUpdate.class) @RequestBody Person person)
            throws Throwable {
        return new ResponseEntity<>(
                persons.patch(person, person.getId(), persons),
                HttpStatus.OK
        );
    }
}
