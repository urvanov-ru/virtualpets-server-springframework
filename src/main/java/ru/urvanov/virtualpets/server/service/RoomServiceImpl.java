/**
 * 
 */
package ru.urvanov.virtualpets.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ru.urvanov.virtualpets.server.dao.AchievementDao;
import ru.urvanov.virtualpets.server.dao.BookDao;
import ru.urvanov.virtualpets.server.dao.BookcaseDao;
import ru.urvanov.virtualpets.server.dao.BuildingMaterialDao;
import ru.urvanov.virtualpets.server.dao.DrinkDao;
import ru.urvanov.virtualpets.server.dao.FoodDao;
import ru.urvanov.virtualpets.server.dao.JournalEntryDao;
import ru.urvanov.virtualpets.server.dao.LevelDao;
import ru.urvanov.virtualpets.server.dao.MachineWithDrinksDao;
import ru.urvanov.virtualpets.server.dao.PetDao;
import ru.urvanov.virtualpets.server.dao.PetJournalEntryDao;
import ru.urvanov.virtualpets.server.dao.RefrigeratorDao;
import ru.urvanov.virtualpets.server.dao.RoomDao;
import ru.urvanov.virtualpets.server.dao.domain.AchievementCode;
import ru.urvanov.virtualpets.server.dao.domain.Book;
import ru.urvanov.virtualpets.server.dao.domain.Bookcase;
import ru.urvanov.virtualpets.server.dao.domain.BookcaseCost;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialType;
import ru.urvanov.virtualpets.server.dao.domain.Cloth;
import ru.urvanov.virtualpets.server.dao.domain.Drink;
import ru.urvanov.virtualpets.server.dao.domain.DrinkType;
import ru.urvanov.virtualpets.server.dao.domain.Food;
import ru.urvanov.virtualpets.server.dao.domain.FoodType;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntry;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryType;
import ru.urvanov.virtualpets.server.dao.domain.Level;
import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks;
import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinksCost;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetBuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.PetDrink;
import ru.urvanov.virtualpets.server.dao.domain.PetFood;
import ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry;
import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;
import ru.urvanov.virtualpets.server.dao.domain.RefrigeratorCost;
import ru.urvanov.virtualpets.server.dao.domain.Room;
import ru.urvanov.virtualpets.server.dao.domain.SelectedPet;
import ru.urvanov.virtualpets.server.service.exception.NotEnoughPetResourcesException;
import ru.urvanov.virtualpets.shared.domain.GetRoomInfoResult;
import ru.urvanov.virtualpets.shared.domain.LevelInfo;
import ru.urvanov.virtualpets.shared.domain.OpenBoxNewbieResult;
import ru.urvanov.virtualpets.shared.domain.Point;
import ru.urvanov.virtualpets.shared.domain.RoomBuildMenuCosts;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;
import ru.urvanov.virtualpets.shared.service.RoomService;

/**
 * @author fedya
 *
 */
@Service
public class RoomServiceImpl implements RoomService {
    
    private static final Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);

    @Autowired
    private RoomDao roomDao;
    
    @Autowired
    private PetDao petDao;
    
    @Autowired
    private PetService petService;

    @Autowired
    private BuildingMaterialDao buildingMaterialDao;

    @Autowired
    private RefrigeratorDao refrigeratorDao;

    @Autowired
    private BookcaseDao bookcaseDao;

    @Autowired
    private MachineWithDrinksDao machineWithDrinksDao;

    @Autowired
    private JournalEntryDao journalEntryDao;

    @Autowired
    private PetJournalEntryDao petJournalEntryDao;
    
    @Autowired
    private FoodDao foodDao;
    
    @Autowired
    private DrinkDao drinkDao;
    
    @Autowired
    private BookDao bookDao;
    
    @Autowired
    private LevelDao levelDao;
    
    @Autowired
    private AchievementDao achievementDao;

    @Autowired
    private ConversionService conversionService;

    /**
     * @return the roomDao
     */
    public RoomDao getRoomDao() {
        return roomDao;
    }

    /**
     * @param roomDao the roomDao to set
     */
    public void setRoomDao(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    private Room findOrCreateByPet(Pet pet) {
        Room room = roomDao.findByPetId(pet.getId());
        if (room == null) {
            room = new Room();
            room.setPetId(pet.getId());
            room.setBoxNewbie1(false);
            room.setBoxNewbie2(false);
            room.setBoxNewbie3(false);
            roomDao.save(room);
        }
        return room;
        
    }


    @Override
    public GetRoomInfoResult getRoomInfo() throws DaoException,
            ServiceException {
        logger.info("getRoomInfo started.");
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findFullById(selectedPet.getId());
        ru.urvanov.virtualpets.shared.domain.GetRoomInfoResult result = new ru.urvanov.virtualpets.shared.domain.GetRoomInfoResult();
        result.setMood(pet.getMood());
        result.setEducation(pet.getEducation());
        result.setSatiety(pet.getSatiety());
        result.setDrink(pet.getDrink());
        result.setNewJournalEntriesCount(petService
                .getPetNewJournalEntriesCount(pet.getId()));

        Cloth hat = pet.getHat();
        Cloth cloth = pet.getCloth();
        Cloth bow = pet.getBow();
        if (hat != null) {
            result.setHatId(hat.getId());
        }
        if (cloth != null) {
            result.setClothId(cloth.getId());
        }
        if (bow != null) {
            result.setBowId(bow.getId());
        }
        LevelInfo levelInfo = new LevelInfo();
        result.setLevelInfo(levelInfo);
        levelInfo.setLevel(pet.getLevel().getId());
        levelInfo.setExperience(pet.getExperience());
        Level nextLevelLeague = levelDao.findById(pet.getLevel().getId() + 1);
        levelInfo.setMaxExperience(nextLevelLeague == null ? Integer.MAX_VALUE : nextLevelLeague.getExperience());
        levelInfo.setMinExperience(pet.getLevel().getExperience());
        
        
        result.setHaveJournal(pet.getJournalEntries().get(
                journalEntryDao.findByCode(JournalEntryType.WELCOME)) != null);
        result.setHaveHammer(pet
                .getJournalEntries()
                .get(journalEntryDao
                        .findByCode(JournalEntryType.BUILD_MACHINE_WITH_DRINKS)) != null);
        result.setHaveRucksack(pet.getJournalEntries().get(
                journalEntryDao
                        .findByCode(JournalEntryType.OPEN_NEWBIE_BOXES)) != null);
        result.setHaveIndicators(pet.getJournalEntries().get(
                journalEntryDao
                        .findByCode(JournalEntryType.DRINK_SOMETHING)) != null);
        result.setHaveToTownArrow(pet.getJournalEntries()
                .get(journalEntryDao
                        .findByCode(JournalEntryType.LEAVE_ROOM)) != null);

        Room room = findOrCreateByPet(pet);
        Refrigerator refrigerator = room.getRefrigerator();
        if (refrigerator != null) {
            result.setRefrigeratorId(refrigerator.getId());
            result.setRefrigeratorX(room.getRefrigeratorX());
            result.setRefrigeratorY(room.getRefrigeratorY());
        }
        boolean[] boxesNewbie = new boolean[3];
        boxesNewbie[0] = room.getBoxNewbie1();
        boxesNewbie[1] = room.getBoxNewbie2();
        boxesNewbie[2] = room.getBoxNewbie3();
        result.setBoxesNewbie(boxesNewbie);
        result.setJournalOnFloor(room.getJournalOnFloor());
        Bookcase bookcase = room.getBookcase();
        if (bookcase != null) {
            result.setBookcaseId(bookcase.getId());
            result.setBookcaseX(room.getBookcaseX());
            result.setBookcaseY(room.getBookcaseY());
        }
        MachineWithDrinks drink = room.getMachineWithDrinks();
        if (drink != null) {
            result.setMachineWithDrinksId(drink.getId());
            result.setMachineWithDrinksX(room.getMachineWithDrinksX());
            result.setMachineWithDrinksY(room.getMachineWithDrinksY());
        }
        
        List<AchievementCode> listServerAchievements = petService.calculateAchievements(pet);
        ru.urvanov.virtualpets.shared.domain.AchievementCode[] listSharedAchievements = new ru.urvanov.virtualpets.shared.domain.AchievementCode[listServerAchievements.size()];
        int n = 0;
        for (AchievementCode ac : listServerAchievements) {
            listSharedAchievements[n] = conversionService.convert(ac, ru.urvanov.virtualpets.shared.domain.AchievementCode.class);
            n++;
        }
        result.setAchievements(listSharedAchievements);
        petDao.save(pet);
        logger.info("getRoomInfo finished.");
        return result;
    }
    

    @Override
    public OpenBoxNewbieResult openBoxNewbie(int index) throws DaoException,
            ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        OpenBoxNewbieResult result = new OpenBoxNewbieResult();
        Map<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer> map = new HashMap<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer>();
        Random random = new Random();
        map.put(ru.urvanov.virtualpets.shared.domain.BuildingMaterialType.TIMBER,
                1 + random.nextInt(2));
        map.put(ru.urvanov.virtualpets.shared.domain.BuildingMaterialType.STONE,
                1 + random.nextInt(2));
        result.setBuildingMaterials(map);
        Room room = roomDao.findByPetId(selectedPet.getId());
        boolean boxNewbie = false;
        switch (index) {
        case 0:
            boxNewbie = room.getBoxNewbie1();
            break;
        case 1:
            boxNewbie = room.getBoxNewbie2();
            break;
        case 2:
            boxNewbie = room.getBoxNewbie3();
            break;
        }
        if (boxNewbie) {
            Pet pet = petDao.findFullById(selectedPet.getId());
            Map<BuildingMaterial, PetBuildingMaterial> petBuildingMaterials = pet
                    .getBuildingMaterials();
            for (Entry<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer> entry : map
                    .entrySet()) {
                BuildingMaterialType buildingMaterialType = conversionService
                        .convert(entry.getKey(), BuildingMaterialType.class);
                BuildingMaterial buildingMaterial = buildingMaterialDao
                        .findByCode(buildingMaterialType);
                PetBuildingMaterial petBuildingMaterial = petBuildingMaterials
                        .get(buildingMaterial);
                if (petBuildingMaterial == null) {
                    petBuildingMaterial = new PetBuildingMaterial();
                    petBuildingMaterial.setBuildingMaterial(buildingMaterial);
                    petBuildingMaterial.setBuildingMaterialCount(entry
                            .getValue());
                    petBuildingMaterial.setPet(pet);
                    petBuildingMaterials.put(buildingMaterial,
                            petBuildingMaterial);
                } else {
                    petBuildingMaterial
                            .setBuildingMaterialCount(petBuildingMaterial
                                    .getBuildingMaterialCount()
                                    + entry.getValue());
                }
            }

            
            switch (index) {
            case 0:
                room.setBoxNewbie1(false);
                break;
            case 1:
                room.setBoxNewbie2(false);
                break;
            case 2:
                room.setBoxNewbie3(false);
                break;
            }
            roomDao.save(room);
            petService.addExperience(pet, 1);
            
            if (!room.getBoxNewbie1() && !room.getBoxNewbie2()
                    && !room.getBoxNewbie3()) {
                PetJournalEntry newPetJournalEntry = new PetJournalEntry();
                newPetJournalEntry.setCreatedAt(new Date());
                newPetJournalEntry.setPet(pet);
                JournalEntry buildMachineWithDrinksJournalEntry = journalEntryDao
                        .findByCode(JournalEntryType.BUILD_MACHINE_WITH_DRINKS);
                newPetJournalEntry
                        .setJournalEntry(buildMachineWithDrinksJournalEntry);
                newPetJournalEntry.setReaded(false);
                pet.getJournalEntries().put(
                        newPetJournalEntry.getJournalEntry(),
                        newPetJournalEntry);
            }
            petDao.save(pet);
        } else {
            throw new ServiceException("This box already opened.");
        }
        result.setIndex(index);
        return result;
    }
    
    @Override
    public void buildRefrigerator(
            ru.urvanov.virtualpets.shared.domain.Point arg)
            throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findFullById(selectedPet.getId());
        Room room = roomDao.findByPetId(pet.getId());
        if (pet.getJournalEntries().get(
                journalEntryDao
                        .findByCode(JournalEntryType.BUILD_REFRIGERATOR)) == null) {
            throw new ServiceException("No now.");
        }
        
        final int DRY_FOOD_ADD_COUNT = 10;
        Food dryFood = foodDao.findByCode(FoodType.DRY_FOOD);
        PetFood petDryFood = pet.getFoods().get(dryFood);
        if (petDryFood == null) {
            petDryFood = new PetFood();
            petDryFood.setFood(dryFood);
            petDryFood.setPet(pet);
            petDryFood.setFoodCount(DRY_FOOD_ADD_COUNT);
            pet.getFoods().put(dryFood, petDryFood);
        } else {
            petDryFood.setFoodCount(petDryFood.getFoodCount() + DRY_FOOD_ADD_COUNT);
        }
        
        
        Refrigerator refrigerator = refrigeratorDao.findFullById(1);
        try {
            petService.substractPetResources(pet, refrigerator);
        } catch (NotEnoughPetResourcesException nepre) {
            throw new ServiceException(nepre.toString());
        }
        room.setRefrigerator(refrigerator);
        room.setRefrigeratorX(arg.getX());
        room.setRefrigeratorY(arg.getY());
        roomDao.save(room);
        JournalEntry eatSomethingJournalEntry = journalEntryDao
                .findByCode(JournalEntryType.EAT_SOMETHING);
        if (pet.getJournalEntries().get(eatSomethingJournalEntry) == null) {
            PetJournalEntry newPetJournalEntry = new PetJournalEntry();
            newPetJournalEntry.setCreatedAt(new Date());
            newPetJournalEntry.setPet(pet);
            newPetJournalEntry.setJournalEntry(eatSomethingJournalEntry);
            newPetJournalEntry.setReaded(false);
            pet.getJournalEntries().put(newPetJournalEntry.getJournalEntry(),
                    newPetJournalEntry);
            petService.addExperience(pet, 1);

        }
        petDao.save(pet);
    }
    


    @Override
    public void moveRefrigerator(Point arg) throws DaoException,
            ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Room room = roomDao.findByPetId(selectedPet.getId());
        if (room.getRefrigerator() == null) {
            throw new ServiceException("No refrigerator in your room.");
        }
        room.setRefrigeratorX(arg.getX());
        room.setRefrigeratorY(arg.getY());
        roomDao.save(room);
    }

    @Override
    public void upgradeRefrigerator() throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findFullById(selectedPet.getId());
        Room room = roomDao.findByPetId(pet.getId());
        if (room.getRefrigerator() == null) {
            throw new ServiceException("No refrigerator in your room.");
        }
        
        Refrigerator refrigerator = refrigeratorDao.findFullById(room
                .getRefrigerator().getId() + 1);
        if (refrigerator == null) {
            throw new ServiceException("Your refrigerator level is max.");
        }
        try {
            petService.substractPetResources(pet, refrigerator);
        } catch (NotEnoughPetResourcesException nepre) {
            throw new ServiceException(nepre.toString());
        }
        room.setRefrigerator(refrigerator);
        roomDao.save(room);
        petService.addExperience(pet, 1);
        petDao.save(pet);
    }

    @Override
    public void buildBookcase(Point arg) throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findFullById(selectedPet.getId());
        Room room = roomDao.findByPetId(pet.getId());
        if (pet.getJournalEntries()
                .get(journalEntryDao
                        .findByCode(JournalEntryType.BUILD_BOOKCASE)) == null) {
            throw new ServiceException("Not now.");
        }
        
        
        Book book = bookDao.findById(1);
        
        if (!pet.getBooks().contains(book)) {
            pet.getBooks().add(book);
        }
        
        Bookcase bookcase = bookcaseDao.findFullById(1);
        try {
            petService.substractPetResources(pet, bookcase);
        } catch (NotEnoughPetResourcesException nepre) {
            throw new ServiceException(nepre);
        }
        room.setBookcase(bookcase);
        room.setBookcaseX(arg.getX());
        room.setBookcaseY(arg.getY());
        roomDao.save(room);
        JournalEntry readSomethingJournalEntry = journalEntryDao
                .findByCode(JournalEntryType.READ_SOMETHING);
        if (pet.getJournalEntries().get(readSomethingJournalEntry) == null) {
            PetJournalEntry newPetJournalEntry = new PetJournalEntry();
            newPetJournalEntry.setCreatedAt(new Date());
            newPetJournalEntry.setPet(pet);
            newPetJournalEntry.setJournalEntry(readSomethingJournalEntry);
            newPetJournalEntry.setReaded(false);
            pet.getJournalEntries().put(newPetJournalEntry.getJournalEntry(),
                    newPetJournalEntry);
            petService.addExperience(pet, 1);
            
            
        }
        petDao.save(pet);
    }

    @Override
    public void upgradeBookcase() throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findFullById(selectedPet.getId());
        Room room = roomDao.findByPetId(pet.getId());
        if (room.getBookcase() == null) {
            throw new ServiceException("No bookcase in your room.");
        }
        Bookcase bookcase = bookcaseDao.findFullById(room.getBookcase()
                .getId() + 1);
        if (bookcase == null) {
            throw new ServiceException("Your bookcase level is max.");
        }
        try {
            petService.substractPetResources(pet, bookcase);
        } catch (NotEnoughPetResourcesException nepre) {
            throw new ServiceException(nepre.toString());
        }
        room.setBookcase(bookcase);
        roomDao.save(room);
        petService.addExperience(pet, 1);
        petDao.save(pet);
    }

    @Override
    public void moveBookcase(Point arg) throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Room room = roomDao.findByPetId(selectedPet.getId());
        if (room.getBookcase() == null) {
            throw new ServiceException("No bookcase in your room.");
        }
        room.setBookcaseX(arg.getX());
        room.setBookcaseY(arg.getY());
        roomDao.save(room);
    }

    @Override
    public void buildMachineWithDrinks(Point arg) throws DaoException,
            ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findFullById(selectedPet.getId());
        Room room = roomDao.findByPetId(pet.getId());
        if (pet.getJournalEntries()
                .get(journalEntryDao
                        .findByCode(JournalEntryType.BUILD_MACHINE_WITH_DRINKS)) == null) {
            throw new ServiceException("Not now.");
        }
        
        final int WATER_ADD_COUNT = 10;
        
        Drink water = drinkDao.findByCode(DrinkType.WATER);
        
        PetDrink petDrink = pet.getDrinks().get(water);
        if (petDrink == null) {
            petDrink = new PetDrink();
            petDrink.setDrink(water);
            petDrink.setPet(pet);
            petDrink.setDrinkCount(WATER_ADD_COUNT);
            pet.getDrinks().put(water, petDrink);
        } else {
            petDrink.setDrinkCount(petDrink.getDrinkCount() + WATER_ADD_COUNT);
        }
        
        
        MachineWithDrinks machineWithDrinks = machineWithDrinksDao
                .findFullById(1);
        try {
            petService.substractPetResources(pet, machineWithDrinks);
        } catch (NotEnoughPetResourcesException nepre) {
            throw new ServiceException(nepre);
        }
        room.setMachineWithDrinks(machineWithDrinks);
        room.setMachineWithDrinksX(arg.getX());
        room.setMachineWithDrinksY(arg.getY());
        roomDao.save(room);
        JournalEntry drinkSomethingJournalEntry = journalEntryDao
                .findByCode(JournalEntryType.DRINK_SOMETHING);
        if (pet.getJournalEntries().get(drinkSomethingJournalEntry) == null) {
            PetJournalEntry newPetJournalEntry = new PetJournalEntry();
            newPetJournalEntry.setCreatedAt(new Date());
            newPetJournalEntry.setPet(pet);
            newPetJournalEntry.setJournalEntry(drinkSomethingJournalEntry);
            newPetJournalEntry.setReaded(false);
            pet.getJournalEntries().put(newPetJournalEntry.getJournalEntry(),
                    newPetJournalEntry);
            petService.addExperience(petDao.findById(pet.getId()), 1);
            petService.addAchievementIfNot(pet, achievementDao.findByCode(AchievementCode.BUILD_1));
            
        }
        petDao.save(pet);
    }

    @Override
    public void moveMachineWithDrinks(Point arg) throws DaoException,
            ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Room room = roomDao.findByPetId(selectedPet.getId());
        if (room.getMachineWithDrinks() == null) {
            throw new ServiceException("No drink in your room.");
        }
        room.setMachineWithDrinksX(arg.getX());
        room.setMachineWithDrinksY(arg.getY());
        roomDao.save(room);
    }

    @Override
    public RoomBuildMenuCosts getBuildMenuCosts() throws DaoException,
            ServiceException {
        RoomBuildMenuCosts roomBuildMenuCosts = new RoomBuildMenuCosts();
        List<Map<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer>> refrigeratorCosts = new ArrayList<Map<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer>>();
        Refrigerator refrigerator = null;
        int index = 1;
        while ((refrigerator = refrigeratorDao.findFullById(index)) != null) {
            index++;
            Map<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer> map = new HashMap<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer>();
            for (Entry<BuildingMaterial, RefrigeratorCost> entry : refrigerator
                    .getRefrigeratorCost().entrySet()) {
                map.put(conversionService
                        .convert(
                                entry.getKey().getCode(),
                                ru.urvanov.virtualpets.shared.domain.BuildingMaterialType.class),
                        entry.getValue().getCost());
            }
            refrigeratorCosts.add(map);
        }
        roomBuildMenuCosts.setRefrigeratorCosts(refrigeratorCosts);

        List<Map<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer>> bookcaseCosts = new ArrayList<Map<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer>>();
        Bookcase bookcase = null;
        index = 1;
        while ((bookcase = bookcaseDao.findFullById(index)) != null) {
            index++;
            Map<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer> map = new HashMap<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer>();
            for (Entry<BuildingMaterial, BookcaseCost> entry : bookcase
                    .getBookcaseCost().entrySet()) {
                map.put(conversionService
                        .convert(
                                entry.getKey().getCode(),
                                ru.urvanov.virtualpets.shared.domain.BuildingMaterialType.class),
                        entry.getValue().getCost());
            }
            bookcaseCosts.add(map);
        }
        roomBuildMenuCosts.setBookcaseCosts(bookcaseCosts);

        List<Map<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer>> drinkCosts = new ArrayList<Map<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer>>();
        MachineWithDrinks drink = null;
        index = 1;
        while ((drink = machineWithDrinksDao.findFullById(index)) != null) {
            index++;
            Map<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer> map = new HashMap<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer>();
            for (Entry<BuildingMaterial, MachineWithDrinksCost> entry : drink
                    .getMachineWithDrinksCost().entrySet()) {
                map.put(conversionService
                        .convert(
                                entry.getKey().getCode(),
                                ru.urvanov.virtualpets.shared.domain.BuildingMaterialType.class),
                        entry.getValue().getCost());
            }
            drinkCosts.add(map);
        }
        roomBuildMenuCosts.setMachineWithDrinksCosts(drinkCosts);
        return roomBuildMenuCosts;

    }

    @Override
    public void upgradeMachineWithDrinks() throws DaoException,
            ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findFullById(selectedPet.getId());
        Room room = roomDao.findByPetId(pet.getId());
        if (room.getMachineWithDrinks() == null) {
            throw new ServiceException("No machine with drinks in your room.");
        }
        MachineWithDrinks machineWithDrinks = machineWithDrinksDao
                .findFullById(room.getMachineWithDrinks().getId() + 1);
        if (machineWithDrinks == null) {
            throw new ServiceException("Your machine with drinks level is max.");
        }
        try {
            petService.substractPetResources(pet, machineWithDrinks);
        } catch (NotEnoughPetResourcesException nepre) {
            throw new ServiceException(nepre.toString());
        }
        room.setMachineWithDrinks(machineWithDrinks);
        roomDao.save(room);
        petService.addExperience(pet, 1);
        petDao.save(pet);
    }

    @Override
    @Transactional
    public void pickJournalOnFloor() throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Room room = roomDao.findByPetId(selectedPet.getId());
        if (room.getJournalOnFloor() == false)
            throw new ServiceException("There isn't any journal in your room.");
        room.setJournalOnFloor(false);
        roomDao.save(room);
        Pet pet = petDao.findById(selectedPet.getId());
        Map<JournalEntry, PetJournalEntry> petJournalEntries = pet
                .getJournalEntries();
        JournalEntry journalEntry = journalEntryDao
                .findByCode(JournalEntryType.WELCOME);
        if (pet.getJournalEntries().get(journalEntry) == null) {
            PetJournalEntry petJournalEntry = new PetJournalEntry();
            petJournalEntry.setCreatedAt(new Date());
            petJournalEntry.setJournalEntry(journalEntry);
            petJournalEntry.setReaded(false);
            petJournalEntry.setPet(pet);
            petJournalEntries.put(journalEntry, petJournalEntry);
            
        }
        petService.addExperience(pet, 1);
        petDao.save(pet);
    }

    @Override
    public void journalClosed() throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet pet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        PetJournalEntry petJournalEntry = petJournalEntryDao
                .findByPetIdAndJournalEntryCode(pet.getId(),
                        JournalEntryType.OPEN_NEWBIE_BOXES);
        if (petJournalEntry == null) {
            Pet fullPet = petDao.findFullById(pet.getId());
            PetJournalEntry newPetJournalEntry = new PetJournalEntry();
            newPetJournalEntry.setCreatedAt(new Date());
            newPetJournalEntry.setPet(fullPet);
            newPetJournalEntry.setJournalEntry(journalEntryDao
                    .findByCode(JournalEntryType.OPEN_NEWBIE_BOXES));
            newPetJournalEntry.setReaded(false);
            fullPet.getJournalEntries().put(
                    newPetJournalEntry.getJournalEntry(), newPetJournalEntry);
            petService.addExperience(fullPet, 1);
            petDao.save(fullPet);
            
            Room room = roomDao.findByPetId(fullPet.getId());
            room.setBoxNewbie1(true);
            room.setBoxNewbie2(true);
            room.setBoxNewbie3(true);
            
            roomDao.save(room);
            
        }
        
        

    }
}
