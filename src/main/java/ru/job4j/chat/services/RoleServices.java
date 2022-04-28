package ru.job4j.chat.services;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.store.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServices {
    private final RoleRepository roles;

    public RoleServices(RoleRepository roles) {
        this.roles = roles;
    }

    public List<Role> findAll() {
        return (List<Role>) roles.findAll();
    }

    public Role save(Role role) {
        return roles.save(role);
    }

    public Optional<Role> findById(int id) {
        return roles.findById(id);
    }

    public void delete(int id) {
        roles.delete(findById(id).get());
    }
}
