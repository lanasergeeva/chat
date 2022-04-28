package ru.job4j.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Person;
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

    private static final String API = "http://localhost:8080/chat/persons/in/";

    private static final String API_ROOM
            = "http://localhost:8080/chat/room/get/{name}";
    @Autowired
    private RestTemplate rest;

    public MessageController(MessageServices messages, RoomServices rooms) {
        this.messages = messages;
        this.rooms = rooms;
    }

    @GetMapping("/room/{room}/people")
    public List<Person> findAllPeople(@PathVariable("room") String name) {
        ResponseEntity<List<Person>> responseEntity =
                rest.exchange(API + name,
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });
        return responseEntity.getBody();
    }

    /**
     * Выводит все сообщения из выбранного чата
     *
     * @param name название команты
     * @return все сообщения в комнате.
     */
    @GetMapping("/room/{room}")
    public List<Message> findAll(@PathVariable("room") String name) {
        Room room = rest.getForEntity(API_ROOM,
                Room.class,
                name).getBody();
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
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.messages.delete(id);
        return ResponseEntity.ok().build();
    }
}
