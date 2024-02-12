/**
 * 
 */
package ru.urvanov.virtualpets.server.service;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.Achievement;
import ru.urvanov.virtualpets.server.dao.domain.AchievementCode;
import ru.urvanov.virtualpets.server.dao.domain.Bookcase;
import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;
import ru.urvanov.virtualpets.server.service.domain.PetDetails;
import ru.urvanov.virtualpets.server.service.exception.NotEnoughPetResourcesException;

/**
 * @author fedya
 * 
 */
public interface PetService {

    public void updatePetsTask();

    void substractPetResources(Pet fullPet, Refrigerator refrigerator)
            throws NotEnoughPetResourcesException;

    void substractPetResources(Pet fullPet, Bookcase bookcase)
            throws NotEnoughPetResourcesException;

    void substractPetResources(Pet fullPet, MachineWithDrinks drink)
            throws NotEnoughPetResourcesException;
    
    public Long getPetNewJournalEntriesCount(Integer petId);

    void addExperience(Pet pet, Integer exp);

    public List<AchievementCode> calculateAchievements(Pet pet);

    List<Pet> findLastCreatedPets(int start, int limit);

    void addAchievementIfNot(Pet pet, Achievement achievement);

    public PetDetails petInformationPage(Integer id);
}
