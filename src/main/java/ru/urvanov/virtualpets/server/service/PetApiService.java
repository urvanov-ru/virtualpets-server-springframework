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
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface PetApiService {
    PetListResult getUserPets() throws DaoException, ServiceException;

    void create(UserDetailsImpl userDetails, SelectedPet selectedPet,
            CreatePetArg arg) throws DaoException, ServiceException;

    void select(UserDetailsImpl userDetails, SelectedPet selectedPet,
            SelectPetArg arg) throws DaoException, ServiceException;

    void drink(UserDetailsImpl userDetails, SelectedPet selectedPet,
            DrinkArg arg) throws DaoException, ServiceException;

    void satiety(UserDetailsImpl userDetails, SelectedPet selectedPet,
            SatietyArg satietyArg) throws DaoException, ServiceException;

    void education(UserDetailsImpl userDetails, SelectedPet selectedPet)
            throws DaoException, ServiceException;

    void mood(UserDetailsImpl userDetails, SelectedPet selectedPet)
            throws DaoException, ServiceException;

    GetPetBooksResult getPetBooks(UserDetailsImpl userDetails,
            SelectedPet selectedPet)
            throws DaoException, ServiceException;

    GetPetClothsResult getPetCloths(UserDetailsImpl userDetails,
            SelectedPet selectedPet)
            throws DaoException, ServiceException;

    void savePetCloths(UserDetailsImpl userDetails,
            SelectedPet selectedPet, SavePetCloths saveClothArg)
            throws DaoException, ServiceException;

    GetPetDrinksResult getPetDrinks(UserDetailsImpl userDetails,
            SelectedPet selectedPet)
            throws DaoException, ServiceException;

    GetPetFoodsResult getPetFoods(UserDetailsImpl userDetails,
            SelectedPet selectedPet)
            throws DaoException, ServiceException;

    GetPetJournalEntriesResult getPetJournalEntries(
            UserDetailsImpl userDetails, SelectedPet selectedPet,
            int count) throws DaoException, ServiceException;

    GetPetRucksackInnerResult getPetRucksackInner(
            UserDetailsImpl userDetails, SelectedPet selectedPet)
            throws DaoException, ServiceException;

    void delete(UserDetailsImpl userDetails, SelectedPet selectedPet,
            Integer petId) throws DaoException, ServiceException;;
}
