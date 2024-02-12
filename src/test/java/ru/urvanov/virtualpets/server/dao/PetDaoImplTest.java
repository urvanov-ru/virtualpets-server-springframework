/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.FoodDao;
import ru.urvanov.virtualpets.server.dao.JournalEntryDao;
import ru.urvanov.virtualpets.server.dao.LevelDao;
import ru.urvanov.virtualpets.server.dao.PetDao;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.Food;
import ru.urvanov.virtualpets.server.dao.domain.FoodType;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntry;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryType;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetFood;
import ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry;
import ru.urvanov.virtualpets.server.dao.domain.PetType;
import ru.urvanov.virtualpets.server.test.annotation.DataSets;

/**
 * @author fedya
 *
 */
public class PetDaoImplTest extends AbstractDaoImplTest {
    
    @Autowired
    private PetDao petDao;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private LevelDao levelDao;
    
    @Autowired
    private JournalEntryDao journalEntryDao;
    
    @Autowired
    private FoodDao foodDao;
        
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetServiceImplTest.xls")
    @Test
    public void testSave() {
        List<Pet> pets = petDao.findByUserId(1);
        int lastSize = pets.size();
        Pet pet = new Pet();
        pet.setName("test4y84hg4");
        pet.setCreatedDate(new Date());
        pet.setLoginDate(new Date());
        pet.setPetType(PetType.CAT);
        pet.setUser(userDao.findByLogin("Clarence"));
        pet.setLevel(levelDao.findById(1));
        petDao.save(pet);
        int newSize = petDao.findByUserId(1).size();
        assertEquals(lastSize + 1, newSize);
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetServiceImplTest.xls")
    @Test
    public void testGetNewJournalEntriesCount() {
        Pet pet = petDao.findById(1);
        Long newJournalEntriesCount = petDao.getPetNewJournalEntriesCount(pet.getId());
        assertEquals(Long.valueOf(0L), newJournalEntriesCount);
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetServiceImplTest.xls")
    @Test
    public void testAddJournalEntry() {
        Pet pet = petDao.findById(1);
        JournalEntry journalEntry = journalEntryDao.findByCode(JournalEntryType.EAT_SOMETHING);
        PetJournalEntry petJournalEntry = new PetJournalEntry();
        petJournalEntry.setJournalEntry(journalEntry);
        petJournalEntry.setReaded(true);
        
        pet.getJournalEntries().put(journalEntry, petJournalEntry);
        //petJournalEntry.setPet(pet);
        petDao.save(pet);
        
        pet = petDao.findFullById(1);
        assertTrue(pet.getJournalEntries().get(journalEntry).getReaded());
    }
    
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetServiceImplTest.xls")
    @Test
    @Transactional
    public void testAddFood() {
        Pet pet = petDao.findById(1);
        Food food = foodDao.findByCode(FoodType.CARROT);
        PetFood petFood = new PetFood();
        petFood.setPet(pet);
        petFood.setFood(food);
        petFood.setFoodCount(10);
        pet.getFoods().put(food,  petFood);
        petDao.save(pet);
        
        pet = petDao.findFullById(1);
        assertEquals(10, pet.getFoods().get(food).getFoodCount());
    }

}
