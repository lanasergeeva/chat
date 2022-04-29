package ru.job4j.chat.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.services.MessageServices;
import ru.job4j.chat.services.RoomServices;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
public class MessageController {
    private final MessageServices messages;
    private final RoomServices rooms;

    public MessageController(MessageServices messages, RoomServices rooms) {
        this.messages = messages;
        this.rooms = rooms;
    }

    /**
     * Выводит все сообщения из выбранного чата
     *
     * @param name название команты
     * @return все сообщения в комнате.
     */
    @GetMapping("/room/{room}")
    public List<Message> findAll(@PathVariable("room") String name) {
        Room room = rooms.findByName(name).get();
        return messages.findAllByRoom(room);
    }

    /**
     * \
     * Метод добавляет сообщение в чат.
     *
     * @param username Имя пользователя
     * @param nameroom Название комнаты
     * @param message  Сообщение пользователя в чат.
     * @return возвращает созданное сообщение
     */
    @PostMapping("/room/{nameroom}/{username}/reply")
    public ResponseEntity<Message> create(
            @PathVariable("username") String username,
            @PathVariable("nameroom") String nameroom,
            @RequestBody Message message) {
        Optional<Room> byName = rooms.findByName(nameroom);
        if (byName.isEmpty()) {
            return ResponseEntity.ok().build();
        }
        message.setRoom(byName.get());
        return new ResponseEntity<>(
                this.messages.save(message, username),
                HttpStatus.CREATED
        );
    }

    /**
     * Удаляет сообщение из чата по айди
     *
     * @param id сообщения
     * @return ok
     */
    @DeleteMapping("/room/{name}/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id, @PathVariable String name) {
        this.messages.delete(id);
        return ResponseEntity.ok().build();
    }
}
