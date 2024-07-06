package ru.urvanov.virtualpets.server.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.urvanov.virtualpets.server.dao.domain.Food;
import ru.urvanov.virtualpets.server.dao.domain.FoodId;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetFood;
import ru.urvanov.virtualpets.server.test.annotation.DataSets;

public class PetFoodDaoImplTest extends AbstractDaoImplTest {

    @Autowired
    private PetFoodDao petFoodDao;

    @Autowired
    private FoodDao foodDao;

    @Autowired
    private PetDao petDao;

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetFoodServiceImplTest.xls")
    @Test
    public void testFindByPetId() {
        List<PetFood> foods = petFoodDao.findByPetId(1);
        assertEquals(foods.size(), 4);
    }

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetFoodServiceImplTest.xls")
    @Test
    public void testFindByPet() {
        Pet pet = petDao.findById(1).orElseThrow();
        assertNotNull(pet);
        List<PetFood> foods = petFoodDao.findByPet(pet);
        assertEquals(foods.size(), 4);
    }

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetFoodServiceImplTest.xls")
    @Test
    public void testFindById() {
        PetFood food = petFoodDao.findById(10).orElseThrow();
        assertNotNull(food);
        assertEquals(food.getId(), Integer.valueOf(10));
    }

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetFoodServiceImplTest.xls")
    @Test
    public void testSave() {
        Food foodCarrot = foodDao.findById(FoodId.CARROT).orElseThrow();
        PetFood petFood = new PetFood();
        Pet pet = petDao.getReference(1);
        petFood.setFood(foodCarrot);
        petFood.setFoodCount(100);
        petFood.setPet(pet);
        petFoodDao.save(petFood);
        assertNotNull(petFood.getId());
    }

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetFoodServiceImplTest.xls")
    @Test
    public void testFindByPetIdAndFoodType() {
        PetFood food = petFoodDao
                .findByPetIdAndFoodType(1, FoodId.DRY_FOOD)
                .orElseThrow();
        assertNotNull(food);
    }

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetFoodServiceImplTest.xls")
    @Test
    public void testFindByPetIdAndFoodType2() {
        Optional<PetFood> petFood = petFoodDao
                .findByPetIdAndFoodType(13463456, FoodId.CHOCOLATE);
        assertTrue(petFood.isEmpty());
    }
}
