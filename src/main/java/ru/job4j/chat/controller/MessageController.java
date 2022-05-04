package ru.job4j.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.auth.IAuthenticationFacade;
import ru.job4j.chat.handler.Operation;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.services.MessageServices;
import ru.job4j.chat.services.RoomServices;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
public class MessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class.getSimpleName());

    private final MessageServices messages;
    private final RoomServices rooms;
    private final ObjectMapper objectMapper;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    public MessageController(MessageServices messages, RoomServices rooms,
                             ObjectMapper objectMapper) {
        this.messages = messages;
        this.rooms = rooms;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/room/{room}")
    public ResponseEntity<List<Message>> findAll(@PathVariable("room") String name) {
        if (rooms.findByName(name).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Room with name %s not found", name));
        }
        List<Message> rsl = messages.findAllByRoom(rooms.findByName(name).get());
        return new ResponseEntity<>(
                rsl,
                HttpStatus.OK
        );
    }

    /**
     * Метод добавляет сообщение в чат.
     *
     * @param username Имя пользователя
     * @param nameroom Название комнаты
     * @param message  Сообщение пользователя в чат.
     * @return возвращает созданное сообщение
     */
    @PostMapping("/room/{nameroom}/{username}/reply")
    public ResponseEntity<Message> create(
            @Validated(Operation.OnCreate.class) @RequestBody Message message,
            @PathVariable("username") String username,
            @PathVariable("nameroom") String nameroom) {
        checkUsername(username);
        Optional<Room> byName = rooms.findByName(nameroom);
        if (byName.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Room with name %s not found", nameroom));
        }
        message.setRoom(byName.get());
        return new ResponseEntity<>(
                this.messages.save(message, username),
                HttpStatus.CREATED
        );
    }

    private void checkUsername(String username) {
        Authentication authentication = authenticationFacade.getAuthentication();
        String name = authentication.getName();
        if (!name.equals(username)) {
            throw new SecurityException("You need to enter your login!");
        }
    }

    /**
     * Удаляет сообщение из чата по айди
     *
     * @param id сообщения
     * @return ok
     */
    @DeleteMapping("/room/{username}/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id, @PathVariable String username) {
        checkUsername(username);
        this.messages.delete(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = {SecurityException.class})
    public void exceptionHandler(Exception e, HttpServletResponse response)
            throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", e.getMessage());
                put("type", e.getClass());
            }
        }));
        LOGGER.error(e.getLocalizedMessage());
    }

    @PatchMapping("/room/message")
    public ResponseEntity<Message> patch(@Validated(Operation.OnUpdate.class)
                                         @RequestBody Message message)
            throws Throwable {
        return new ResponseEntity<>(
                messages.patch(message, message.getId(), messages),
                HttpStatus.OK
        );
    }
}
