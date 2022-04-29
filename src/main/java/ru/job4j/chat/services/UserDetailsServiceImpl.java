package ru.job4j.chat.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Person;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl
        implements UserDetailsService {
    private final PersonServices persons;

    public UserDetailsServiceImpl(PersonServices persons) {
        this.persons = persons;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person user = persons.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getLogin(), user.getPassword(), emptyList());
    }
}
