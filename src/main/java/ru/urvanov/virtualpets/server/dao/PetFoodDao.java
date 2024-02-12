/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.FoodType;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetFood;

/**
 * @author fedya
 *
 */
public interface PetFoodDao {
    public PetFood findById(Integer id);
    public List<PetFood> findByPetId(Integer petId);
    public List<PetFood> findByPet(Pet pet);
    public void save(PetFood food);
    public PetFood findByPetIdAndFoodType(Integer petId, FoodType foodType);
}
