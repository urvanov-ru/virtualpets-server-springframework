package ru.urvanov.virtualpets.server.controller.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
import ru.urvanov.virtualpets.shared.service.PetService;

@RestController("restPetController")
@RequestMapping("rest/v1/PetService")
public class PetController {
    
    @Autowired
    private PetService petService;
    
    @RequestMapping(value = "getUserPets", method = RequestMethod.GET)
    public PetListResult getUserPets() throws DaoException,
            ServiceException {
        return petService.getUserPets();
    }
    
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public CreatePetResult create(@RequestBody CreatePetArg createPetArg) throws DaoException,
            ServiceException {
        return petService.create(createPetArg);
    }
    
    @RequestMapping(value = "select", method = RequestMethod.POST)
    public SelectPetResult select(@RequestBody SelectPetArg selectPetArg) throws DaoException,
            ServiceException {
        return petService.select(selectPetArg);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "drink", method = RequestMethod.POST)
    public void drink(@RequestBody DrinkArg drinkArg) throws DaoException,
            ServiceException {
        petService.drink(drinkArg);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "satiety", method = RequestMethod.POST)
    public void eat(@RequestBody SatietyArg satietyArg) throws DaoException,
            ServiceException {
        petService.satiety(satietyArg);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "education", method = RequestMethod.POST)
    public void education() throws DaoException,
            ServiceException {
        petService.education();
    }
    
    @GetMapping(value = "getPetBooks")
    public GetPetBooksResult getPetBooks() throws DaoException, ServiceException {
        return petService.getPetBooks();
    }
    
    @GetMapping(value = "getPetCloths")
    public GetPetClothsResult getPetCloths() throws DaoException, ServiceException {
        return petService.getPetCloths();
    }
    
    @PostMapping(value = "savePetCloths")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void savePetCloth(SavePetCloths saveClothArg) throws DaoException, ServiceException {
        petService.savePetCloths(saveClothArg);
    }
    
    @GetMapping(value = "getPetDrinks")
    public GetPetDrinksResult getPetDrinks() throws DaoException, ServiceException {
        return petService.getPetDrinks();
    }
    
    @GetMapping(value = "getPetFoods")
    public GetPetFoodsResult getPetFoods() throws DaoException, ServiceException {
        return petService.getPetFoods();
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "getPetJournalEntries")
    public GetPetJournalEntriesResult getPetJournalEntries(@RequestParam(name = "count") int count) {
        return petService.getPetJournalEntries(count);
    }
    
    @GetMapping(value = "getPetRucksackInner")
    public GetPetRucksackInnerResult getPetRucksackInner() throws DaoException, ServiceException {
        return petService.getPetRucksackInner();
    }
}


