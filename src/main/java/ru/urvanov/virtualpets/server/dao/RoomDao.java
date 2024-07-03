package ru.urvanov.virtualpets.server.dao;

import java.util.Optional;

import ru.urvanov.virtualpets.server.dao.domain.Room;

public interface RoomDao {

    Optional<Room> findByPetId(Integer petId);

    void save(Room room);

    void delete(Room room);

    long existsByPetId(Integer petId);
}
