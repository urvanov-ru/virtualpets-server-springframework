package ru.urvanov.virtualpets.server.dao;

import ru.urvanov.virtualpets.server.dao.domain.Room;

public interface RoomDao {
    Room findByPetId(Integer petId);
    void save(Room room);
    void delete(Room room);
}
