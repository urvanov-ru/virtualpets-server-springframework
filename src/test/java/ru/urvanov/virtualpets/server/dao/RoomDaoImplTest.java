/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.urvanov.virtualpets.server.dao.PetDao;
import ru.urvanov.virtualpets.server.dao.RoomDao;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.Room;
import ru.urvanov.virtualpets.server.test.annotation.DataSets;

/**
 * @author fedya
 *
 */
public class RoomDaoImplTest extends AbstractDaoImplTest {
    
    @Autowired
    private RoomDao roomDao;
    
    @Autowired
    private PetDao petDao;

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RoomServiceImplTest.xls")
    @Test
    public void testFind1() {
        Room room = roomDao.findByPetId(1);
        assertNotNull(room);
        assertNotNull(room.getPetId());
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RoomServiceImplTest.xls")
    @Test
    public void testFind2() {
        Room room = roomDao.findByPetId(-1);
        assertNull(room);
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RoomServiceImplTest.xls")
    @Test
    public void testSaveNew() {
        Room room = new Room();
        Pet pet = petDao.findById(2);
        room.setPetId(pet.getId());
        room.setBoxNewbie1(true);
        room.setBoxNewbie2(true);
        room.setBoxNewbie3(true);
        roomDao.save(room);
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RoomServiceImplTest.xls")
    @Test
    public void testSaveExist() {
        Room room = roomDao.findByPetId(1);
        room.setBoxNewbie1(false);
        roomDao.save(room);
        room = roomDao.findByPetId(1);
        assertEquals(room.getBoxNewbie1(), false);
    }

}
