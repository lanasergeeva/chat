package ru.job4j.chat.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/persons/in/{room}")
    public List<Person> findAllPeople(@PathVariable("room") String name) {
        return persons.findAllPeople(name);
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = this.persons.findById(id);
        return new ResponseEntity<>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    /**
     * Создает пользователя. Если роль не задана, то присвается роль с айди 1 (weak_user).
     *
     * @param person тело пользователя.
     * @return ResponseEntity<Person>
     */
    @PostMapping("/persons")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return new ResponseEntity<>(
                this.persons.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/persons")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        this.persons.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.persons.delete(id);
        return ResponseEntity.ok().build();
    }
}