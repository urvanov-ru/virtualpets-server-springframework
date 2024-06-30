package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.api.domain.CreatePetArg;
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
import ru.urvanov.virtualpets.server.auth.UserDetailsImpl;
import ru.urvanov.virtualpets.server.dao.exception.DaoException;
import ru.urvanov.virtualpets.server.service.domain.SelectedPet;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface PetApiService {
    PetListResult getUserPets(UserPetDetails userPetDetails)
            throws DaoException, ServiceException;

    void create(UserPetDetails userPetDetails, CreatePetArg arg)
            throws DaoException, ServiceException;

    void select(UserPetDetails userPetDetails, SelectPetArg arg)
            throws DaoException, ServiceException;

    void drink(UserPetDetails userPetDetails, DrinkArg arg)
            throws DaoException, ServiceException;

    void satiety(UserPetDetails userPetDetails, SatietyArg satietyArg)
            throws DaoException, ServiceException;

    void education(UserPetDetails userPetDetails)
            throws DaoException, ServiceException;

    void mood(UserPetDetails userPetDetails)
            throws DaoException, ServiceException;

    GetPetBooksResult getPetBooks(UserPetDetails userPetDetails)
            throws DaoException, ServiceException;

    GetPetClothsResult getPetCloths(UserPetDetails userPetDetails)
            throws DaoException, ServiceException;

    void savePetCloths(UserPetDetails userPetDetails, SavePetCloths saveClothArg)
            throws DaoException, ServiceException;

    GetPetDrinksResult getPetDrinks(UserPetDetails userPetDetails)
            throws DaoException, ServiceException;

    GetPetFoodsResult getPetFoods(UserPetDetails userPetDetails)
            throws DaoException, ServiceException;

    GetPetJournalEntriesResult getPetJournalEntries(
            UserPetDetails userPetDetails, int count)
            throws DaoException, ServiceException;

    GetPetRucksackInnerResult getPetRucksackInner(
            UserPetDetails userPetDetails)
            throws DaoException, ServiceException;

    void delete(UserPetDetails userPetDetails, Integer petId)
            throws DaoException, ServiceException;;
}
