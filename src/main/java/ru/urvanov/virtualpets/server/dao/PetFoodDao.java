package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Optional;

import ru.urvanov.virtualpets.server.dao.domain.FoodId;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetFood;

public interface PetFoodDao {

    Optional<PetFood> findById(Integer id);

    List<PetFood> findByPetId(Integer petId);

    List<PetFood> findFullByPetId(Integer petId);

    List<PetFood> findByPet(Pet pet);

    void save(PetFood food);

    Optional<PetFood> findByPetIdAndFoodType(Integer petId,
            FoodId foodType);
}
