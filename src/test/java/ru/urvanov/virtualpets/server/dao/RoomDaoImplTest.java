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
public class RoomDaoImplTest extends BaseDaoImplTest {
    
    @Autowired
    private RoomDao roomDao;
    
    @Autowired
    private PetDao petDao;

    @Test
    public void testFind1() {
        Room room = roomDao.findByPetId(1).orElseThrow();
        assertNotNull(room);
        assertNotNull(room.getPetId());
    }
    
    @Test
    public void testFind2() {
        Optional<Room> room = roomDao.findByPetId(-1);
        assertTrue(room.isEmpty());
    }
    
    @Test
    public void testSaveNew() {
        Room room = new Room();
        Pet pet = petDao.findById(2).orElseThrow();
        room.setPetId(pet.getId());
        room.setBoxNewbie1(true);
        room.setBoxNewbie2(true);
        room.setBoxNewbie3(true);
        roomDao.save(room);
        Room actual = roomDao.findByPetId(2).orElseThrow();
        assertNotNull(actual);
    }
    
    @Test
    public void testSaveExist() {
        Room room = roomDao.findByPetId(1).orElseThrow();
        room.setBoxNewbie1(false);
        roomDao.save(room);
        room = roomDao.findByPetId(1).orElseThrow();
        assertFalse(room.isBoxNewbie1());
    }

}
