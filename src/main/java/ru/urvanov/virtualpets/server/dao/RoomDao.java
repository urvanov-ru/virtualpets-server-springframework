/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import ru.urvanov.virtualpets.server.dao.domain.Room;

/**
 * @author fedya
 *
 */
public interface RoomDao {
    public Room findByPetId(Integer petId);
    public void save(Room room);
}
