package ru.job4j.chat.services;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.store.PersonRepository;
import ru.job4j.chat.store.RoleRepository;


import java.util.List;
import java.util.Optional;

@Service
public class PersonServices implements Services<Person> {
    private final PersonRepository persons;
    private final RoleRepository roles;

    public PersonServices(PersonRepository persons, RoleRepository roles) {
        this.persons = persons;
        this.roles = roles;
    }

    @Override
    public List<Person> findAll() {
        return (List<Person>) persons.findAll();
    }

    @Override
    public Person save(Person person) {
        if (person.getRole() == null) {
            Role role = roles.findById(1).get();
            person.setRole(role);
        }
        return persons.save(person);
    }

    @Override
    public Optional<Person> findById(int id) {
        return persons.findById(id);
    }

    @Override
    public void delete(int id) {
        persons.delete(findById(id).get());
    }

    public Person findByLogin(String name) {
        return persons.findByLogin(name);
    }

    public List<Person> findAllPeople(String room) {
        return persons.findAllByRoomName(room);
    }

    /**
     * Валидация входящего Person  с помощью GlobalExceptionHandler.java
     *
     * @param person входящий пользователь.
     */
    @Override
    public void check(Person person) {
        String login = person.getLogin();
        String password = person.getPassword();
        if (login == null || password == null) {
            throw new NullPointerException("Login and password mustn't be empty");
        }
        if (password.length() < 3 || password.length() > 11) {
            throw new IllegalArgumentException("Length of password must be more then 3 and less then 11");
        }
    }

}
