package ru.job4j.chat.services;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.store.RoomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServices implements Services<Room> {
    private final RoomRepository rooms;

    public RoomServices(RoomRepository rooms) {
        this.rooms = rooms;
    }

    @Override
    public List<Room> findAll() {
        return (List<Room>) rooms.findAll();
    }

    @Override
    public Room save(Room room) {
        return rooms.save(room);
    }

    @Override
    public Optional<Room> findById(int id) {
        return rooms.findById(id);
    }

    @Override
    public void delete(int id) {
        rooms.delete(findById(id).get());
    }

    public Optional<Room> findByName(String name) {
        return rooms.findOneByName(name);
    }

    /**
     * Валидация имени роли с помощью GlobalExceptionHandler
     * @param room входящая роль.
     */
    @Override
    public void check(Room room) {
        String name = room.getName();
        if (name == null) {
            throw new NullPointerException("Name of room mustn't be empty");
        }
        if (name.length() < 5) {
            throw new IllegalArgumentException("Length must be more then 5");
        }
    }

}
