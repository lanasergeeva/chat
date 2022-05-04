package ru.job4j.chat.services;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.store.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServices implements Services<Message> {
    private final MessageRepository messages;
    private final PersonServices persons;

    public MessageServices(MessageRepository messages, PersonServices persons) {
        this.messages = messages;
        this.persons = persons;
    }

    @Override
    public List<Message> findAll() {
        return (List<Message>) messages.findAll();
    }

    @Override
    public Message save(Message message) {
        return messages.save(message);
    }

    public Message save(Message message, String name) {
        Person byLogin = persons.findByLogin(name);
        message.setPerson(byLogin);
        return save(message);
    }

    @Override
    public Optional<Message> findById(int id) {
        return messages.findById(id);
    }

    @Override
    public void delete(int id) {
        messages.delete(findById(id).get());
    }

    public List<Message> findAllByRoom(Room room) {
        return messages.findAllByRoom(room);
    }

    @Override
    public void check(Message message) {
        String text = message.getText();
        if (text == null || text.length() < 2) {
            throw new IllegalArgumentException("Text mustn't be null and length must be  more then 2");
        }
    }
}

