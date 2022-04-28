package ru.job4j.chat.store;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Person;

import java.util.List;

public interface PersonRepository
        extends CrudRepository<Person, Integer> {
    Person findByLogin(String name);

    @Query("SELECT DISTINCT p FROM Person p "
            + "LEFT JOIN FETCH p.messages m "
            + " WHERE m.room.id = (select r.id from Room r where r.name like ?1)")
    List<Person> findAllByRoomName(String name);



}
