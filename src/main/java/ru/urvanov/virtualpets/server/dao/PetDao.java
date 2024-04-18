package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.Pet;

public interface PetDao {
    
    Pet findById(Integer id);
    Pet findFullById(Integer id);
    void save(Pet pet);
    void delete(Integer petId);
    
    List<Pet> findByUserId(Integer userId);
    Pet getReference(Integer id);
    void updatePetsTask();
    Long getPetNewJournalEntriesCount(Integer petId);
    List<Pet> findLastCreatedPets(int start, int limit);
    Pet findByIdWithBuildingMaterials(Integer id);
    Pet findByIdWithFullBuildingMaterials(Integer id);
    Pet findByIdWithFullFoods(Integer id);
    Pet findByIdWithFullDrinks(Integer id);
    Pet findByIdWithFullCloths(Integer id);
    Pet findByIdWithFullBooks(Integer id);
    Pet findByIdWithJournalEntriesAndAchievements(Integer id);
    
    Pet findByIdWithBooksAndJournalEntriesAndBuildingMaterials(Integer id);
    Pet findByIdWithFoodsAndJournalEntriesAndBuildingMaterials(Integer id);
    Pet findByIdWithDrinksAndJournalEntriesAndBuildingMaterialsAndAchievements(
            Integer id);
}
