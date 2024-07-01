package ru.urvanov.virtualpets.server.controller.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
import ru.urvanov.virtualpets.server.service.PetApiService;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@RestController("restPetController")
@RequestMapping("rest/v1/PetService")
public class PetController {

    @Autowired
    private PetApiService petService;

    @Autowired
    private UserPetDetails userPetDetails;

    @RequestMapping(value = "getUserPets", method = RequestMethod.GET)
    public PetListResult getUserPets(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
            throws DaoException, ServiceException {
        return petService.getUserPets(userPetDetails);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public void create(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody CreatePetArg createPetArg)
            throws DaoException, ServiceException {
        petService.create(userPetDetails, createPetArg);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "select", method = RequestMethod.POST)
    public void select(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody SelectPetArg selectPetArg)
            throws DaoException, ServiceException {
        petService.select(userPetDetails, selectPetArg);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "delete/{petId}", method = RequestMethod.DELETE)
    public void delete(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @PathVariable Integer petId)
            throws DaoException, ServiceException {
        petService.delete(userPetDetails, petId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "drink", method = RequestMethod.POST)
    public void drink(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody DrinkArg drinkArg)
            throws DaoException, ServiceException {
        petService.drink(userPetDetails, drinkArg);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "satiety", method = RequestMethod.POST)
    public void eat(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody SatietyArg satietyArg)
            throws DaoException, ServiceException {
        petService.satiety(userPetDetails, satietyArg);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "education", method = RequestMethod.POST)
    public void education() throws DaoException, ServiceException {
        petService.education(userPetDetails);
    }

    @GetMapping(value = "getPetBooks")
    public GetPetBooksResult getPetBooks()
            throws DaoException, ServiceException {
        return petService.getPetBooks(userPetDetails);
    }

    @GetMapping(value = "getPetCloths")
    public GetPetClothsResult getPetCloths()
            throws DaoException, ServiceException {
        return petService.getPetCloths(userPetDetails);
    }

    @PostMapping(value = "savePetCloths")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void savePetCloth(@RequestBody SavePetCloths saveClothArg)
            throws DaoException, ServiceException {
        petService.savePetCloths(userPetDetails, saveClothArg);
    }

    @GetMapping(value = "getPetDrinks")
    public GetPetDrinksResult getPetDrinks()
            throws DaoException, ServiceException {
        return petService.getPetDrinks(userPetDetails);
    }

    @GetMapping(value = "getPetFoods")
    public GetPetFoodsResult getPetFoods()
            throws DaoException, ServiceException {
        return petService.getPetFoods(userPetDetails);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getPetJournalEntries")
    public GetPetJournalEntriesResult getPetJournalEntries(
            @RequestParam(name = "count") int count)
            throws DaoException, ServiceException {
        return petService.getPetJournalEntries(userPetDetails, count);
    }

    @GetMapping(value = "getPetRucksackInner")
    public GetPetRucksackInnerResult getPetRucksackInner()
            throws DaoException, ServiceException {
        return petService.getPetRucksackInner(userPetDetails);
    }
}
