package ru.urvanov.virtualpets.server.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Food;
import ru.urvanov.virtualpets.server.dao.domain.FoodId;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryId;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetFood;
import ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry;
import ru.urvanov.virtualpets.server.dao.domain.PetType;
import ru.urvanov.virtualpets.server.test.annotation.DataSets;

public class PetDaoImplTest extends AbstractDaoImplTest {

    @Autowired
    private PetDao petDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LevelDao levelDao;

    @Autowired
    private FoodDao foodDao;

    @Autowired
    private Clock clock;

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetServiceImplTest.xls")
    @Test
    public void testSave() {
        List<Pet> pets = petDao.findByUserId(1);
        int lastSize = pets.size();
        Pet pet = new Pet();
        pet.setName("test4y84hg4");
        pet.setCreatedDate(OffsetDateTime.now(clock));
        pet.setLoginDate(OffsetDateTime.now(clock));
        pet.setPetType(PetType.CAT);
        pet.setUser(userDao.findByLogin("Clarence").orElseThrow());
        pet.setLevel(levelDao.findById(1).orElseThrow());
        petDao.save(pet);
        int newSize = petDao.findByUserId(1).size();
        assertEquals(lastSize + 1, newSize);
    }

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetServiceImplTest.xls")
    @Test
    public void testGetNewJournalEntriesCount() {
        Pet pet = petDao.findById(1).orElseThrow();
        Long newJournalEntriesCount = petDao
                .getPetNewJournalEntriesCount(pet.getId());
        assertEquals(Long.valueOf(0L), newJournalEntriesCount);
    }

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetServiceImplTest.xls")
    @Test
    public void testAddJournalEntry() {
        Pet pet = petDao.findById(1).orElseThrow();
        PetJournalEntry petJournalEntry = new PetJournalEntry();
        petJournalEntry.setCreatedAt(OffsetDateTime.now(clock));
        petJournalEntry.setJournalEntry(JournalEntryId.WELCOME);
        petJournalEntry.setPet(pet);
        petJournalEntry.setReaded(true);

        pet.getJournalEntries().put(JournalEntryId.WELCOME,
                petJournalEntry);
        petDao.save(pet);

        pet = petDao.findFullById(1).orElseThrow();
        assertTrue(pet.getJournalEntries().get(JournalEntryId.WELCOME)
                .isReaded());
    }

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetServiceImplTest.xls")
    @Test
    @Transactional
    public void testAddFood() {
        Pet pet = petDao.findById(1).orElseThrow();
        Food food = foodDao.getReference(FoodId.CARROT);
        PetFood petFood = new PetFood();
        petFood.setPet(pet);
        petFood.setFood(food);
        petFood.setFoodCount(10);
        pet.getFoods().put(FoodId.CARROT, petFood);
        petDao.save(pet);

        pet = petDao.findFullById(1).orElseThrow();
        assertEquals(10,
                pet.getFoods().get(FoodId.CARROT).getFoodCount());
    }

}
