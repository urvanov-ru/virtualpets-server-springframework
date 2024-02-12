/**
 * 
 */
package ru.urvanov.virtualpets.shared.service;

import ru.urvanov.virtualpets.shared.domain.CreatePetArg;
import ru.urvanov.virtualpets.shared.domain.CreatePetResult;
import ru.urvanov.virtualpets.shared.domain.DrinkArg;
import ru.urvanov.virtualpets.shared.domain.GetPetBooksResult;
import ru.urvanov.virtualpets.shared.domain.GetPetClothsResult;
import ru.urvanov.virtualpets.shared.domain.GetPetDrinksResult;
import ru.urvanov.virtualpets.shared.domain.GetPetFoodsResult;
import ru.urvanov.virtualpets.shared.domain.GetPetJournalEntriesResult;
import ru.urvanov.virtualpets.shared.domain.GetPetRucksackInnerResult;
import ru.urvanov.virtualpets.shared.domain.PetListResult;
import ru.urvanov.virtualpets.shared.domain.SatietyArg;
import ru.urvanov.virtualpets.shared.domain.SavePetCloths;
import ru.urvanov.virtualpets.shared.domain.SelectPetArg;
import ru.urvanov.virtualpets.shared.domain.SelectPetResult;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;

/**
 * @author fedya
 *
 */
public interface PetService {
    public PetListResult getUserPets() throws DaoException, ServiceException;
    public CreatePetResult create(CreatePetArg arg) throws DaoException, ServiceException;
    public SelectPetResult select(SelectPetArg arg) throws DaoException, ServiceException;
    public void drink(DrinkArg arg) throws DaoException, ServiceException;
    public void satiety(SatietyArg satietyArg) throws DaoException, ServiceException;
    public void education() throws DaoException, ServiceException;
    public void mood() throws DaoException, ServiceException;
    
    GetPetBooksResult getPetBooks() throws DaoException, ServiceException;
    
    public GetPetClothsResult getPetCloths() throws DaoException, ServiceException;
    public void savePetCloths(SavePetCloths saveClothArg) throws DaoException, ServiceException;
    
    public GetPetDrinksResult getPetDrinks() throws DaoException, ServiceException;
    
    public GetPetFoodsResult getPetFoods() throws DaoException, ServiceException;
    
    GetPetJournalEntriesResult getPetJournalEntries(int count);
    
    public GetPetRucksackInnerResult getPetRucksackInner() throws DaoException, ServiceException;
}
