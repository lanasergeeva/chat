package ru.job4j.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.job4j.chat.handler.Operation;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1, message = "Id must be more then 0",
            groups = Operation.OnUpdate.class)
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Name of room must be not empty", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class})
    private String name;

    @OneToMany(mappedBy = "room",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> messages = new HashSet<>();

    public static Room of(String name) {
        Room room = new Room();
        room.setName(name);
        return room;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Set<Message> getMessages() {
        return messages;
    }

    @JsonProperty
    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Room{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", messages=" + messages
                + '}';
    }
}
