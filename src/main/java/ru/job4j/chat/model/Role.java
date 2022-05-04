package ru.job4j.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.job4j.chat.handler.Operation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id must be than 0", groups = {
            Operation.OnUpdate.class})
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Name of role must be not empty", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class})
    @Size(min = 5, message = "Name of role must be more than 5 characters",
            groups = {Operation.OnCreate.class, Operation.OnUpdate.class})
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<Person> persons = new HashSet<>();

    public static Role of(String name) {
        Role role = new Role();
        role.setName(name);
        return role;
    }

    public void addPerson(Person person) {
        persons.add(person);
    }

    @JsonIgnore
    public int getId() {
        return id;
    }

    @JsonProperty
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
    public Set<Person> getPersons() {
        return persons;
    }

    @JsonProperty
    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "Role{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", persons=" + persons
                + '}';
    }
}
