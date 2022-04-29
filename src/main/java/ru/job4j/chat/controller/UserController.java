package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.services.PersonServices;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/chat/persons")
public class UserController {
    private final PersonServices users;
    private final BCryptPasswordEncoder encoder;

    public UserController(PersonServices users, BCryptPasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    /**
     * Создает пользователя. Если роль не задана, то присвается роль с айди 1 (weak_user).
     * Кодирует пароль.
     * @param person тело пользователя.
     * @return ResponseEntity<Person>
     */
    @PostMapping("/sign-up")
    public ResponseEntity<Person> signUp(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        return new ResponseEntity<>(
                this.users.save(person),
                HttpStatus.CREATED
        );
    }
}

