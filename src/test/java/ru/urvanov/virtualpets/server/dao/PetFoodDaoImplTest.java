/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.urvanov.virtualpets.server.dao.FoodDao;
import ru.urvanov.virtualpets.server.dao.PetDao;
import ru.urvanov.virtualpets.server.dao.PetFoodDao;
import ru.urvanov.virtualpets.server.dao.domain.Food;
import ru.urvanov.virtualpets.server.dao.domain.FoodType;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetFood;
import ru.urvanov.virtualpets.server.test.annotation.DataSets;

/**
 * @author fedya
 *
 */
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
        Pet pet = petDao.findById(1);
        assertNotNull(pet);
        List<PetFood> foods = petFoodDao.findByPet(pet);
        assertEquals(foods.size(), 4);
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetFoodServiceImplTest.xls")
    @Test
    public void testFindById() {
        PetFood food = petFoodDao.findById(10);
        assertNotNull(food);
        assertEquals(food.getId(), Integer.valueOf(10));
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetFoodServiceImplTest.xls")
    @Test
    public void testSave() {
        Food foodCarrot = foodDao.findByCode(FoodType.CARROT);
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
        PetFood food = petFoodDao.findByPetIdAndFoodType(1, FoodType.DRY_FOOD);
        assertNotNull(food);
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetFoodServiceImplTest.xls")
    @Test
    public void testFindByPetIdAndFoodType2() {
        PetFood food = petFoodDao.findByPetIdAndFoodType(13463456, FoodType.CHOCOLATE);
        assertNull(food);
    }
}
