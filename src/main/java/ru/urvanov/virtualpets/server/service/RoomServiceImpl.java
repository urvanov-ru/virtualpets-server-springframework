package ru.urvanov.virtualpets.server.service;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ru.urvanov.virtualpets.server.dao.BookDao;
import ru.urvanov.virtualpets.server.dao.BookcaseDao;
import ru.urvanov.virtualpets.server.dao.BuildingMaterialDao;
import ru.urvanov.virtualpets.server.dao.DrinkDao;
import ru.urvanov.virtualpets.server.dao.FoodDao;
import ru.urvanov.virtualpets.server.dao.LevelDao;
import ru.urvanov.virtualpets.server.dao.MachineWithDrinksDao;
import ru.urvanov.virtualpets.server.dao.PetDao;
import ru.urvanov.virtualpets.server.dao.PetJournalEntryDao;
import ru.urvanov.virtualpets.server.dao.RefrigeratorDao;
import ru.urvanov.virtualpets.server.dao.RoomDao;
import ru.urvanov.virtualpets.server.dao.domain.AchievementId;
import ru.urvanov.virtualpets.server.dao.domain.Book;
import ru.urvanov.virtualpets.server.dao.domain.Bookcase;
import ru.urvanov.virtualpets.server.dao.domain.BookcaseCost;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId;
import ru.urvanov.virtualpets.server.dao.domain.Cloth;
import ru.urvanov.virtualpets.server.dao.domain.DrinkId;
import ru.urvanov.virtualpets.server.dao.domain.FoodId;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryId;
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
    private ConversionService conversionService;
    
    @Autowired
    private Clock clock;
    
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
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
    public GetRoomInfoResult getRoomInfo() throws DaoException,
            ServiceException {
        logger.info("getRoomInfo started.");
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findByIdWithJournalEntriesAndAchievements(selectedPet.getId());
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
                JournalEntryId.WELCOME) != null);
        result.setHaveHammer(pet
                .getJournalEntries()
                .get(JournalEntryId.BUILD_MACHINE_WITH_DRINKS) != null);
        result.setHaveRucksack(pet.getJournalEntries().get(
                JournalEntryId.OPEN_NEWBIE_BOXES) != null);
        result.setHaveIndicators(pet.getJournalEntries().get(
                JournalEntryId.DRINK_SOMETHING) != null);
        result.setHaveToTownArrow(pet.getJournalEntries()
                .get(JournalEntryId.LEAVE_ROOM) != null);

        Room room = findOrCreateByPet(pet);
        Refrigerator refrigerator = room.getRefrigerator();
        if (refrigerator != null) {
            result.setRefrigeratorId(refrigerator.getId());
            result.setRefrigeratorX(room.getRefrigeratorX());
            result.setRefrigeratorY(room.getRefrigeratorY());
        }
        boolean[] boxesNewbie = new boolean[3];
        boxesNewbie[0] = room.isBoxNewbie1();
        boxesNewbie[1] = room.isBoxNewbie2();
        boxesNewbie[2] = room.isBoxNewbie3();
        result.setBoxesNewbie(boxesNewbie);
        result.setJournalOnFloor(room.isJournalOnFloor());
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
        
        List<AchievementId> listServerAchievements = petService.calculateAchievements(pet);
        
        List<ru.urvanov.virtualpets.shared.domain.AchievementCode> listSharedAchievements = listServerAchievements.stream()
                .map(ac -> conversionService.convert(ac, ru.urvanov.virtualpets.shared.domain.AchievementCode.class))
                .collect(Collectors.toList());
        result.setAchievements(listSharedAchievements);
        logger.info("getRoomInfo finished.");
        return result;
    }
    

    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
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
            boxNewbie = room.isBoxNewbie1();
            break;
        case 1:
            boxNewbie = room.isBoxNewbie2();
            break;
        case 2:
            boxNewbie = room.isBoxNewbie3();
            break;
        }
        if (boxNewbie) {
            Pet pet = petDao.findFullById(selectedPet.getId());
            Map<BuildingMaterialId, PetBuildingMaterial> petBuildingMaterials = pet
                    .getBuildingMaterials();
            for (Entry<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer> entry : map
                    .entrySet()) {
                BuildingMaterialId buildingMaterialType = conversionService
                        .convert(entry.getKey(), BuildingMaterialId.class);
                PetBuildingMaterial petBuildingMaterial = petBuildingMaterials
                        .get(buildingMaterialType);
                if (petBuildingMaterial == null) {
                    petBuildingMaterial = new PetBuildingMaterial();
                    petBuildingMaterial.setBuildingMaterial(buildingMaterialDao.getReference(buildingMaterialType));
                    petBuildingMaterial.setBuildingMaterialCount(entry
                            .getValue());
                    petBuildingMaterial.setPet(pet);
                    petBuildingMaterials.put(buildingMaterialType,
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
            petService.addExperience(pet, 1);
            
            if (!room.isBoxNewbie1() && !room.isBoxNewbie2()
                    && !room.isBoxNewbie3()) {
                PetJournalEntry newPetJournalEntry = new PetJournalEntry();
                newPetJournalEntry.setCreatedAt(OffsetDateTime.now(clock));
                newPetJournalEntry.setPet(pet);
                newPetJournalEntry
                        .setJournalEntry(JournalEntryId.BUILD_MACHINE_WITH_DRINKS);
                newPetJournalEntry.setReaded(false);
                pet.getJournalEntries().put(
                        newPetJournalEntry.getJournalEntry(),
                        newPetJournalEntry);
            }
        } else {
            throw new ServiceException("This box already opened.");
        }
        result.setIndex(index);
        return result;
    }
    
    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
    public void buildRefrigerator(
            ru.urvanov.virtualpets.shared.domain.Point arg)
            throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findFullById(selectedPet.getId());
        Room room = roomDao.findByPetId(pet.getId());
        if (!pet.getJournalEntries().containsKey(JournalEntryId.BUILD_REFRIGERATOR)) {
            throw new ServiceException("No now.");
        }
        
        final int DRY_FOOD_ADD_COUNT = 10;
        PetFood petDryFood = pet.getFoods().get(FoodId.DRY_FOOD);
        if (petDryFood == null) {
            petDryFood = new PetFood();
            petDryFood.setFood(foodDao.getReference(FoodId.DRY_FOOD));
            petDryFood.setPet(pet);
            petDryFood.setFoodCount(DRY_FOOD_ADD_COUNT);
            pet.getFoods().put(FoodId.DRY_FOOD, petDryFood);
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
        if (!pet.getJournalEntries().containsKey(JournalEntryId.EAT_SOMETHING)) {
            PetJournalEntry newPetJournalEntry = new PetJournalEntry();
            newPetJournalEntry.setCreatedAt(OffsetDateTime.now(clock));
            newPetJournalEntry.setPet(pet);
            newPetJournalEntry.setJournalEntry(JournalEntryId.EAT_SOMETHING);
            newPetJournalEntry.setReaded(false);
            pet.getJournalEntries().put(newPetJournalEntry.getJournalEntry(),
                    newPetJournalEntry);
            petService.addExperience(pet, 1);

        }
    }
    


    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
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
    }

    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
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
        petService.addExperience(pet, 1);
    }

    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
    public void buildBookcase(Point arg) throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findFullById(selectedPet.getId());
        Room room = roomDao.findByPetId(pet.getId());
        if (!pet.getJournalEntries()
                .containsKey(JournalEntryId.BUILD_BOOKCASE)) {
            throw new ServiceException("Not now.");
        }
        
        
        Book book = bookDao.findById("DESTINY");
        
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
        if (!pet.getJournalEntries().containsKey(JournalEntryId.READ_SOMETHING)) {
            PetJournalEntry newPetJournalEntry = new PetJournalEntry();
            newPetJournalEntry.setCreatedAt(OffsetDateTime.now(clock));
            newPetJournalEntry.setPet(pet);
            newPetJournalEntry.setJournalEntry(JournalEntryId.READ_SOMETHING);
            newPetJournalEntry.setReaded(false);
            pet.getJournalEntries().put(newPetJournalEntry.getJournalEntry(),
                    newPetJournalEntry);
            petService.addExperience(pet, 1);
            
            
        }
    }

    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
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
        petService.addExperience(pet, 1);
    }

    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
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
    }

    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
    public void buildMachineWithDrinks(Point arg) throws DaoException,
            ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findFullById(selectedPet.getId());
        Room room = roomDao.findByPetId(pet.getId());
        if (!pet.getJournalEntries()
                .containsKey(JournalEntryId.BUILD_MACHINE_WITH_DRINKS)) {
            throw new ServiceException("Not now.");
        }
        
        final int WATER_ADD_COUNT = 10;
        
        PetDrink petDrink = pet.getDrinks().get(DrinkId.WATER);
        if (petDrink == null) {
            petDrink = new PetDrink();
            petDrink.setDrink(drinkDao.getReference(DrinkId.WATER));
            petDrink.setPet(pet);
            petDrink.setDrinkCount(WATER_ADD_COUNT);
            pet.getDrinks().put(DrinkId.WATER, petDrink);
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
        if (!pet.getJournalEntries().containsKey(JournalEntryId.DRINK_SOMETHING)) {
            PetJournalEntry newPetJournalEntry = new PetJournalEntry();
            newPetJournalEntry.setCreatedAt(OffsetDateTime.now(clock));
            newPetJournalEntry.setPet(pet);
            newPetJournalEntry.setJournalEntry(JournalEntryId.DRINK_SOMETHING);
            newPetJournalEntry.setReaded(false);
            pet.getJournalEntries().put(newPetJournalEntry.getJournalEntry(),
                    newPetJournalEntry);
            petService.addExperience(pet, 1);
            petService.addAchievementIfNot(pet, AchievementId.BUILD_1);
            
        }
    }

    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
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
            for (Entry<BuildingMaterialId, RefrigeratorCost> entry : refrigerator
                    .getRefrigeratorCost().entrySet()) {
                map.put(conversionService
                        .convert(
                                entry.getKey(),
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
            for (Entry<BuildingMaterialId, BookcaseCost> entry : bookcase
                    .getBookcaseCost().entrySet()) {
                map.put(conversionService
                        .convert(
                                entry.getKey(),
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
            for (Entry<BuildingMaterialId, MachineWithDrinksCost> entry : drink
                    .getMachineWithDrinksCost().entrySet()) {
                map.put(conversionService
                        .convert(
                                entry.getKey(),
                                ru.urvanov.virtualpets.shared.domain.BuildingMaterialType.class),
                        entry.getValue().getCost());
            }
            drinkCosts.add(map);
        }
        roomBuildMenuCosts.setMachineWithDrinksCosts(drinkCosts);
        return roomBuildMenuCosts;

    }

    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
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
        petService.addExperience(pet, 1);
    }

    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
    public void pickJournalOnFloor() throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Room room = roomDao.findByPetId(selectedPet.getId());
        if (room.isJournalOnFloor() == false)
            throw new ServiceException("There isn't any journal in your room.");
        room.setJournalOnFloor(false);
        Pet pet = petDao.findById(selectedPet.getId());
        Map<JournalEntryId, PetJournalEntry> petJournalEntries = pet
                .getJournalEntries();
        if (!pet.getJournalEntries().containsKey(JournalEntryId.WELCOME)) {
            PetJournalEntry petJournalEntry = new PetJournalEntry();
            petJournalEntry.setCreatedAt(OffsetDateTime.now(clock));
            petJournalEntry.setJournalEntry(JournalEntryId.WELCOME);
            petJournalEntry.setReaded(false);
            petJournalEntry.setPet(pet);
            petJournalEntries.put(JournalEntryId.WELCOME, petJournalEntry);
            
        }
        petService.addExperience(pet, 1);
    }

    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
    public void journalClosed() throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet pet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        PetJournalEntry petJournalEntry = petJournalEntryDao
                .findByPetIdAndJournalEntryCode(pet.getId(),
                        JournalEntryId.OPEN_NEWBIE_BOXES);
        if (petJournalEntry == null) {
            Pet fullPet = petDao.findFullById(pet.getId());
            PetJournalEntry newPetJournalEntry = new PetJournalEntry();
            newPetJournalEntry.setCreatedAt(OffsetDateTime.now(clock));
            newPetJournalEntry.setPet(fullPet);
            newPetJournalEntry.setJournalEntry(JournalEntryId.OPEN_NEWBIE_BOXES);
            newPetJournalEntry.setReaded(false);
            fullPet.getJournalEntries().put(
                    newPetJournalEntry.getJournalEntry(), newPetJournalEntry);
            petService.addExperience(fullPet, 1);
            
            Room room = roomDao.findByPetId(fullPet.getId());
            room.setBoxNewbie1(true);
            room.setBoxNewbie2(true);
            room.setBoxNewbie3(true);
        }
        
        

    }
}
