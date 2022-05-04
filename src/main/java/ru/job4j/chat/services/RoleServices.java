package ru.job4j.chat.services;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.store.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServices implements Services<Role> {
    private final RoleRepository roles;

    public RoleServices(RoleRepository roles) {
        this.roles = roles;
    }

    @Override
    public List<Role> findAll() {
        return (List<Role>) roles.findAll();
    }

    @Override
    public Role save(Role role) {
        return roles.save(role);
    }

    @Override
    public Optional<Role> findById(int id) {
        return roles.findById(id);
    }

    @Override
    public void delete(int id) {
        roles.delete(findById(id).get());
    }

    /**
     * Валидация Role с помощью GlobalExceptionHandler.java
     *
     * @param role входящая роль
     */
    @Override
    public void check(Role role) {
        String name = role.getName();
        if (name == null) {
            throw new NullPointerException("Name of role mustn't be empty");
        }
        if (name.length() < 5) {
            throw new IllegalArgumentException("Length must be more then 4");
        }
    }
}
