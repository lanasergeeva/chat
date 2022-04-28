package ru.job4j.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
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
