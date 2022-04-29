package ru.job4j.chat.store;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Role;

public interface RoleRepository
        extends CrudRepository<Role, Integer>  {
}
