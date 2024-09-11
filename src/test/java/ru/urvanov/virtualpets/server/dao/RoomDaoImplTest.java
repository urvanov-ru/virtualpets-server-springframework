package ru.urvanov.virtualpets.server.dao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.Room;

@Sql({ "/ru/urvanov/virtualpets/server/clean.sql",
        "RoomDaoImplTest.sql" })
class RoomDaoImplTest extends BaseDaoImplTest {
    
    @Autowired
    private RoomDao roomDao;
    
    @Autowired
    private PetDao petDao;

    @Test
    void testFind1() {
        Optional<Room> room = roomDao.findByPetId(1);
        assertTrue(room.isPresent());
        assertNotNull(room.map(Room::getPetId).orElse(null));
    }
    
    @Test
    void testFind2() {
        Optional<Room> room = roomDao.findByPetId(-1);
        assertTrue(room.isEmpty());
    }
    
    @Test
    void testSaveNew() {
        Room room = new Room();
        Pet pet = petDao.findById(2).orElseThrow();
        room.setPetId(pet.getId());
        room.setBoxNewbie1(true);
        room.setBoxNewbie2(true);
        room.setBoxNewbie3(true);
        roomDao.save(room);
        Optional<Room> actual = roomDao.findByPetId(2);
        assertTrue(actual.isPresent());
    }
    
    @Test
    void testSaveExist() {
        Room room = roomDao.findByPetId(1).orElseThrow();
        room.setBoxNewbie1(false);
        roomDao.save(room);
        room = roomDao.findByPetId(1).orElseThrow();
        assertFalse(room.isBoxNewbie1());
    }

}
