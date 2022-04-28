package ru.job4j.chat.services;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.store.PersonRepository;
import ru.job4j.chat.store.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServices {
    private final PersonRepository persons;
    private final RoleRepository roles;

    public PersonServices(PersonRepository persons, RoleRepository roles) {
        this.persons = persons;
        this.roles = roles;
    }

    public List<Person> findAll() {
        return (List<Person>) persons.findAll();
    }

    public Person save(Person person) {
        if (person.getRole() == null) {
            Role role = roles.findById(1).get();
            person.setRole(role);
        }
        return persons.save(person);
    }

    public Optional<Person> findById(int id) {
        return persons.findById(id);
    }

    public void delete(int id) {
        persons.delete(findById(id).get());
    }

    public Person findByLogin(String name) {
        return persons.findByLogin(name);
    }

    public List<Person> findAllPeople(String room) {
        return persons.findAllByRoomName(room);
    }

}
