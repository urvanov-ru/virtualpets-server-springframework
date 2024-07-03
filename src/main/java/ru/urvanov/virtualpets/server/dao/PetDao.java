package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Optional;

import ru.urvanov.virtualpets.server.dao.domain.Pet;

public interface PetDao {

    Optional<Pet> findById(Integer id);

    Optional<Pet> findFullById(Integer id);

    void save(Pet pet);

    void delete(Integer petId);

    List<Pet> findByUserId(Integer userId);

    Pet getReference(Integer id);

    void updatePetsTask();

    Long getPetNewJournalEntriesCount(Integer petId);

    List<Pet> findLastCreatedPets(int start, int limit);

    Optional<Pet> findByIdWithBuildingMaterials(Integer id);

    Optional<Pet> findByIdWithFullBuildingMaterials(Integer id);

    Optional<Pet> findByIdWithFullFoods(Integer id);

    Optional<Pet> findByIdWithFullDrinks(Integer id);

    Optional<Pet> findByIdWithFullCloths(Integer id);

    Optional<Pet> findByIdWithFullBooks(Integer id);

    Optional<Pet> findByIdWithJournalEntriesAndAchievements(Integer id);

    Optional<Pet> findByIdWithBooksAndJournalEntriesAndBuildingMaterials(
            Integer id);

    Optional<Pet> findByIdWithFoodsAndJournalEntriesAndBuildingMaterials(
            Integer id);

    Optional<Pet> findByIdWithDrinksAndJournalEntriesAndBuildingMaterialsAndAchievements(
            Integer id);

    Optional<Pet> findByIdWithDrinksAndJournalEntriesAndAchievements(
            Integer id);

    Optional<Pet> findByIdWithFoodsJournalEntriesAndAchievements(
            Integer id);
}
