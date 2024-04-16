package ru.urvanov.virtualpets.server.dao;

import ru.urvanov.virtualpets.server.dao.domain.Room;

public interface RoomDao {
    Room findByPetId(Integer petId);
    Room findByPetIdOrNull(Integer petId);
    void save(Room room);
    void delete(Room room);
    long existsByPetId(Integer petId);
}
