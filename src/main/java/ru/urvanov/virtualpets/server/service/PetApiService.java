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
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface PetApiService {
    PetListResult getUserPets(UserPetDetails userPetDetails)
            throws ServiceException;

    void create(UserPetDetails userPetDetails, CreatePetArg arg)
            throws ServiceException;

    void select(UserPetDetails userPetDetails, SelectPetArg arg)
            throws ServiceException;

    void drink(UserPetDetails userPetDetails, DrinkArg arg)
            throws ServiceException;

    void satiety(UserPetDetails userPetDetails, SatietyArg satietyArg)
            throws ServiceException;

    void education(UserPetDetails userPetDetails)
            throws ServiceException;

    void mood(UserPetDetails userPetDetails)
            throws ServiceException;

    GetPetBooksResult getPetBooks(UserPetDetails userPetDetails)
            throws ServiceException;

    GetPetClothsResult getPetCloths(UserPetDetails userPetDetails)
            throws ServiceException;

    void savePetCloths(UserPetDetails userPetDetails, SavePetCloths saveClothArg)
            throws ServiceException;

    GetPetDrinksResult getPetDrinks(UserPetDetails userPetDetails)
            throws ServiceException;

    GetPetFoodsResult getPetFoods(UserPetDetails userPetDetails)
            throws ServiceException;

    GetPetJournalEntriesResult getPetJournalEntries(
            UserPetDetails userPetDetails, int count)
            throws ServiceException;

    GetPetRucksackInnerResult getPetRucksackInner(
            UserPetDetails userPetDetails)
            throws ServiceException;

    void delete(UserPetDetails userPetDetails, Integer petId)
            throws ServiceException;;
}
