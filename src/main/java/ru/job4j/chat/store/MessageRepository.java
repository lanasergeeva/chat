package ru.job4j.chat.store;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Room;

import java.util.List;

public interface MessageRepository
        extends CrudRepository<Message, Integer> {
    List<Message> findAllByRoom(Room room);
}
