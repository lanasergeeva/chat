package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
     *
     * @return возвращает лист со всеми комантами.
     */
    @GetMapping("/room/all")
    public List<Room> findAll() {
        return rooms.findAll();
    }

    /**
     * Находит комнату по имени.
     *
     * @param name название комнаты
     * @return пустой или найденный объект.
     */
    @GetMapping("/room/get/{name}")
    public ResponseEntity<Room> findRoom(@PathVariable String name) {
        return rooms.findByName(name)
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Room with name %s not found", name)));
    }

    /**
     * Создает новую комнату.
     *
     * @param room тело Room
     * @return созданный Room.
     */
    @PostMapping("admin/room")
    public ResponseEntity<Room> create(@RequestBody Room room) {
        String name = room.getName();
        if (name == null) {
            throw new NullPointerException("Name of room mustn't be empty");
        }
        if (name.length() < 5) {
            throw new IllegalArgumentException("Length must be more then 5");
        }
        return new ResponseEntity<>(
                this.rooms.save(room),
                HttpStatus.CREATED
        );
    }

    /**
     * Удаляет комнату по имени
     *
     * @param name имя команты.
     * @return ok.
     */
    @DeleteMapping("admin/room/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        Optional<Room> byName = rooms.findByName(name);
        if (byName.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Room with name %s not found", name));
        }
        Room room = byName.get();
        this.rooms.delete(room.getId());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("admin/room")
    public ResponseEntity<Room> patch(@RequestBody Room room)
            throws Throwable {
        return new ResponseEntity<>(
                rooms.patch(room, room.getId(), rooms),
                HttpStatus.OK
        );
    }
}
