package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.handler.Operation;
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
        return roles.findById(id)
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Role with id %d not found", id)));
    }

    @PostMapping("/role")
    public ResponseEntity<Role> create(@Validated(Operation.OnCreate.class)
                                           @RequestBody Role role) {
        return new ResponseEntity<>(
                this.roles.save(role),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/role")
    public ResponseEntity<Void> update(@Validated(Operation.OnUpdate.class)
                                           @RequestBody Role role) {
        this.roles.save(role);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (roles.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Role with id %d not found", id));
        }
        this.roles.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/role")
    public ResponseEntity<Role> patch(@Validated(Operation.OnUpdate.class)
                                      @RequestBody Role role)
            throws Throwable {
        return new ResponseEntity<>(
                roles.patch(role, role.getId(), roles),
                HttpStatus.OK
        );
    }
}
