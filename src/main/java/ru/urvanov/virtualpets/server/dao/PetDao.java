/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.Pet;

/**
 * @author fedya
 *
 */
public interface PetDao {
    
    public Pet findById(Integer id);
    public Pet findFullById(Integer id);
    public void save(Pet pet);
    public void delete(Integer id);
    
    public List<Pet> findByUserId(Integer userId);
    public Pet getReference(Integer id);
    public void updatePetsTask();
    public Long getPetNewJournalEntriesCount(Integer petId);
    public List<Pet> findLastCreatedPets(int start, int limit);
}
