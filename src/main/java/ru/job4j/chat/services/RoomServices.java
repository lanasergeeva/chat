package ru.job4j.chat.services;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.store.RoomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServices {
    private final RoomRepository rooms;

    public RoomServices(RoomRepository rooms) {
        this.rooms = rooms;
    }

    public List<Room> findAll() {
        return (List<Room>) rooms.findAll();
    }

    public Room save(Room room) {
        return rooms.save(room);
    }

    public Optional<Room> findById(int id) {
        return rooms.findById(id);
    }

    public void delete(int id) {
        rooms.delete(findById(id).get());
    }

    public Optional<Room> findByName(String name) {
        return rooms.findOneByName(name);
    }
}
