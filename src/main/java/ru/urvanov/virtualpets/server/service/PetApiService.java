package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.api.domain.CreatePetArg;
import ru.urvanov.virtualpets.server.api.domain.CreatePetResult;
import ru.urvanov.virtualpets.server.api.domain.DrinkArg;
import ru.urvanov.virtualpets.server.api.domain.GetPetBooksResult;
import ru.urvanov.virtualpets.server.api.domain.GetPetClothsResult;
import ru.urvanov.virtualpets.server.api.domain.GetPetDrinksResult;
import ru.urvanov.virtualpets.server.api.domain.GetPetFoodsResult;
import ru.urvanov.virtualpets.server.api.domain.GetPetJournalEntriesResult;
import ru.urvanov.virtualpets.server.api.domain.GetPetRucksackInnerResult;
import ru.urvanov.virtualpets.server.api.domain.PetListResult;
import ru.urvanov.virtualpets.server.api.domain.SatietyArg;
import ru.urvanov.virtualpets.server.api.domain.SavePetCloths;
import ru.urvanov.virtualpets.server.api.domain.SelectPetArg;
import ru.urvanov.virtualpets.server.api.domain.SelectPetResult;
import ru.urvanov.virtualpets.server.dao.exception.DaoException;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface PetApiService {
    PetListResult getUserPets() throws DaoException, ServiceException;

    CreatePetResult create(CreatePetArg arg)
            throws DaoException, ServiceException;

    SelectPetResult select(SelectPetArg arg)
            throws DaoException, ServiceException;

    void drink(DrinkArg arg) throws DaoException, ServiceException;

    void satiety(SatietyArg satietyArg)
            throws DaoException, ServiceException;

    void education() throws DaoException, ServiceException;

    void mood() throws DaoException, ServiceException;

    GetPetBooksResult getPetBooks()
            throws DaoException, ServiceException;

    GetPetClothsResult getPetCloths()
            throws DaoException, ServiceException;

    void savePetCloths(SavePetCloths saveClothArg)
            throws DaoException, ServiceException;

    GetPetDrinksResult getPetDrinks()
            throws DaoException, ServiceException;

    GetPetFoodsResult getPetFoods()
            throws DaoException, ServiceException;

    GetPetJournalEntriesResult getPetJournalEntries(int count)
            throws DaoException, ServiceException;

    GetPetRucksackInnerResult getPetRucksackInner()
            throws DaoException, ServiceException;

    void delete(Integer petId) throws DaoException, ServiceException;;
}
