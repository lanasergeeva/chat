package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.services.RoleServices;

import java.util.List;

@RestController
@RequestMapping("/chat/admin")
public class RoleController {
    private final RoleServices roles;

    public RoleController(RoleServices roles) {
        this.roles = roles;
    }

    @GetMapping("/role")
    public List<Role> findAll() {
        return roles.findAll();
    }


    @GetMapping("/role/{id}")
    public ResponseEntity<Role> findById(@PathVariable int id) {
        var role = this.roles.findById(id);
        return new ResponseEntity<>(
                role.orElse(new Role()),
                role.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/role")
    public ResponseEntity<Role> create(@RequestBody Role role) {
        return new ResponseEntity<>(
                this.roles.save(role),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/role")
    public ResponseEntity<Void> update(@RequestBody Role role) {
        this.roles.save(role);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.roles.delete(id);
        return ResponseEntity.ok().build();
    }
}
