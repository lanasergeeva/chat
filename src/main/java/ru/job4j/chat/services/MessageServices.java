package ru.job4j.chat.services;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.store.MessageRepository;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServices {
    private final MessageRepository messages;
    private final PersonServices persons;

    public MessageServices(MessageRepository messages, PersonServices persons) {
        this.messages = messages;
        this.persons = persons;
    }

    public List<Message> findAll() {
        return (List<Message>) messages.findAll();
    }

    public Message save(Message message, String name) {
        Person byLogin = persons.findByLogin(name);
        message.setPerson(byLogin);
        return messages.save(message);
    }

    public Optional<Message> findById(int id) {
        return messages.findById(id);
    }

    public void delete(int id) {
        messages.delete(findById(id).get());
    }

    public List<Message> findAllByRoom(Room room) {
        return messages.findAllByRoom(room);
    }
}
