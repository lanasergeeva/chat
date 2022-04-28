package ru.job4j.chat.store;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Room;

import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Integer> {
    Optional<Room> findOneByName(String name);
}
