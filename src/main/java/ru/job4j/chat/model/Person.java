package ru.job4j.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.job4j.chat.handler.Operation;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1, message = "Id must be more then 0",
            groups = Operation.OnUpdate.class)
    private int id;

    @Size(min = 3, message = "Login must be more than 2 characters",
            groups = {Operation.OnCreate.class, Operation.OnUpdate.class})
    @NotBlank(message = "Login must be not empty", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class})
    @Column(name = "login")
    private String login;

    @Size(min = 5, max = 10,
            message = "Password must be more than 4 and less then 11",
            groups = {Operation.OnCreate.class, Operation.OnUpdate.class})
    @NotBlank(message = "Password must be not empty", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class})
    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "person",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> messages = new HashSet<>();

    public static Person of(String login, String password) {
        Person person = new Person();
        person.setLogin(login);
        person.setPassword(password);
        return person;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public Role getRole() {
        return role;
    }

    @JsonProperty
    public void setRole(Role role) {
        this.role = role;
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
        return "Person{" + "id=" + id
                + ", login='" + login + '\''
                + ", password='" + password + '\''
                + ", role=" + role
                + ", messages=" + messages
                + '}';
    }
}
