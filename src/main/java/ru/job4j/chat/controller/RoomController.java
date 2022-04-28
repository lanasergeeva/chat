package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.services.RoomServices;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
public class RoomController {

    private final RoomServices rooms;

    public RoomController(RoomServices rooms) {
        this.rooms = rooms;
    }

    /**
     * Метод находит все комнаты.
     * @return возвращает лист со всеми комантами.
     */
    @GetMapping("/room/all")
    public List<Room> findAll() {
        return rooms.findAll();
    }

    /**
     * Находит комнату по имени.
     * @param name название комнаты
     * @return пустой или найденный объект.
     */
    @GetMapping("/room/get/{name}")
    public ResponseEntity<Room> findRoom(@PathVariable String name) {
        var room = this.rooms.findByName(name);
        return new ResponseEntity<>(
                room.orElse(new Room()),
                room.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    /**
     * Создает новую комнату.
     * @param room тело Room
     * @return созданный Room.
     */
    @PostMapping("admin/room")
    public ResponseEntity<Room> create(@RequestBody Room room) {
        return new ResponseEntity<>(
                this.rooms.save(room),
                HttpStatus.CREATED
        );
    }

    /**
     * Удаляет комнату по имени
     * @param name имя команты.
     * @return ok.
     */
    @DeleteMapping("admin/room/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        Optional<Room> byName = rooms.findByName(name);
        if (byName.isPresent()) {
            Room room = byName.get();
            this.rooms.delete(room.getId());
        }
        return ResponseEntity.ok().build();
    }
}
