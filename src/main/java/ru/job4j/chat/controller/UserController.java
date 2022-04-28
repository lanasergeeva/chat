/*
package ru.job4j.chat.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.store.PersonRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

*/
/*@RestController
@RequestMapping("/chat/users")*//*

public class UserController {
    private PersonRepository users;
    private BCryptPasswordEncoder encoder;

    public UserController(PersonRepository users,
                          BCryptPasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        users.save(person);
    }

    @GetMapping("/all")
    public List<Person> findAll() {
        return (List<Person>) users.findAll();
    }
}

*/
