package ru.urvanov.virtualpets.server.service;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.api.domain.GetRoomInfoResult;
import ru.urvanov.virtualpets.server.api.domain.LevelInfo;
import ru.urvanov.virtualpets.server.api.domain.OpenBoxNewbieResult;
import ru.urvanov.virtualpets.server.api.domain.Point;
import ru.urvanov.virtualpets.server.api.domain.RoomBuildMenuCosts;
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
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@Service
public class RoomServiceImpl implements RoomApiService {
    
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
    private Clock clock;

    private Room findOrCreateByPet(Pet pet) {
        Optional<Room> room = roomDao.findByPetId(pet.getId());
        return room.orElseGet(() -> {
                Room r = new Room();
                r.setPetId(pet.getId());
                r.setBoxNewbie1(false);
                r.setBoxNewbie2(false);
                r.setBoxNewbie3(false);
                roomDao.save(r);
                return r;
            });
    }


    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public GetRoomInfoResult getRoomInfo(UserPetDetails userPetDetails)
            throws ServiceException {
        logger.info("getRoomInfo started.");
        Pet pet = petDao.findByIdWithJournalEntriesAndAchievements(userPetDetails.getPetId())
                .orElseThrow();
        ru.urvanov.virtualpets.server.api.domain.GetRoomInfoResult result = new ru.urvanov.virtualpets.server.api.domain.GetRoomInfoResult();
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
        
        Level nextLevelLeague = levelDao.findById(pet.getLevel().getId() + 1)
                .orElseThrow();
        LevelInfo levelInfo = new LevelInfo(
                pet.getLevel().getId(),
                pet.getExperience(),
                pet.getLevel().getExperience(),
                nextLevelLeague == null ? Integer.MAX_VALUE : nextLevelLeague.getExperience());
        result.setLevelInfo(levelInfo);
        
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
        
        List<AchievementId> achievements = petService.calculateAchievements(pet);
        result.setAchievements(achievements);
        logger.info("getRoomInfo finished.");
        return result;
    }
    

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public OpenBoxNewbieResult openBoxNewbie(UserPetDetails userPetDetails, int index)
            throws ServiceException {
        
        Map<BuildingMaterialId, Integer> map
                = new HashMap<BuildingMaterialId, Integer>();
        Random random = new Random();
        map.put(BuildingMaterialId.TIMBER,
                1 + random.nextInt(2));
        map.put(BuildingMaterialId.STONE,
                1 + random.nextInt(2));
        Room room = roomDao.findByPetId(userPetDetails.getPetId())
                .orElseThrow();
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
            Pet pet = petDao.findFullById(userPetDetails.getPetId())
                    .orElseThrow();
            Map<BuildingMaterialId, PetBuildingMaterial> petBuildingMaterials = pet
                    .getBuildingMaterials();
            for (Entry<BuildingMaterialId, Integer> entry : map
                    .entrySet()) {
                BuildingMaterialId buildingMaterialId = entry.getKey();
                PetBuildingMaterial petBuildingMaterial = petBuildingMaterials
                        .get(buildingMaterialId);
                if (petBuildingMaterial == null) {
                    petBuildingMaterial = new PetBuildingMaterial();
                    petBuildingMaterial.setBuildingMaterial(
                            buildingMaterialDao.getReference(
                                    buildingMaterialId));
                    petBuildingMaterial.setBuildingMaterialCount(entry
                            .getValue());
                    petBuildingMaterial.setPet(pet);
                    petBuildingMaterials.put(buildingMaterialId,
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
        return new OpenBoxNewbieResult(index, map);
    }
    
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void buildRefrigerator(UserPetDetails userPetDetails,
            ru.urvanov.virtualpets.server.api.domain.Point position)
            throws ServiceException {
        Pet pet = petDao.findByIdWithFoodsAndJournalEntriesAndBuildingMaterials(
                userPetDetails.getPetId()).orElseThrow();
        Room room = roomDao.findByPetId(pet.getId()).orElseThrow();
        if (!pet.getJournalEntries()
                .containsKey(JournalEntryId.BUILD_REFRIGERATOR)) {
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
        
        
        Refrigerator refrigerator = refrigeratorDao.findFullById(1)
                .orElseThrow();
        petService.substractPetResources(pet, refrigerator);
        room.setRefrigerator(refrigerator);
        room.setRefrigeratorX(position.x());
        room.setRefrigeratorY(position.y());
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
    @Transactional(rollbackFor = ServiceException.class)
    public void moveRefrigerator(UserPetDetails userPetDetails, Point position)
            throws ServiceException {
        Room room = roomDao.findByPetId(userPetDetails.getPetId())
                .orElseThrow();
        if (room.getRefrigerator() == null) {
            throw new ServiceException("No refrigerator in your room.");
        }
        room.setRefrigeratorX(position.x());
        room.setRefrigeratorY(position.y());
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void upgradeRefrigerator(UserPetDetails userPetDetails)
            throws ServiceException {
        Pet pet = petDao.findFullById(userPetDetails.getPetId())
                .orElseThrow();
        Room room = roomDao.findByPetId(pet.getId()).orElseThrow();
        if (room.getRefrigerator() == null) {
            throw new ServiceException("No refrigerator in your room.");
        }
        
        Refrigerator refrigerator = refrigeratorDao.findFullById(room
                .getRefrigerator().getId() + 1)
                .orElseThrow();
        if (refrigerator == null) {
            throw new ServiceException("Your refrigerator level is max.");
        }
        petService.substractPetResources(pet, refrigerator);
        room.setRefrigerator(refrigerator);
        petService.addExperience(pet, 1);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void buildBookcase(
            UserPetDetails userPetDetails, Point position)
            throws ServiceException {
        Pet pet = petDao.findByIdWithBooksAndJournalEntriesAndBuildingMaterials(userPetDetails.getPetId())
                .orElseThrow();
        Room room = roomDao.findByPetId(pet.getId())
                .orElseThrow();
        if (!pet.getJournalEntries()
                .containsKey(JournalEntryId.BUILD_BOOKCASE)) {
            throw new ServiceException("Not now.");
        }
        
        
        Book book = bookDao.findById("DESTINY");
        
        if (!pet.getBooks().contains(book)) {
            pet.getBooks().add(book);
        }
        
        Bookcase bookcase = bookcaseDao.findFullById(1).orElseThrow();
        petService.substractPetResources(pet, bookcase);
        room.setBookcase(bookcase);
        room.setBookcaseX(position.x());
        room.setBookcaseY(position.y());
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
    @Transactional(rollbackFor = ServiceException.class)
    public void upgradeBookcase(UserPetDetails userPetDetails)
            throws ServiceException {
        Pet pet = petDao.findFullById(userPetDetails.getPetId())
                .orElseThrow();
        Room room = roomDao.findByPetId(pet.getId())
                .orElseThrow();
        if (room.getBookcase() == null) {
            throw new ServiceException("No bookcase in your room.");
        }
        Bookcase bookcase = bookcaseDao.findFullById(room.getBookcase()
                .getId() + 1).orElseThrow();
        if (bookcase == null) {
            throw new ServiceException("Your bookcase level is max.");
        }
        petService.substractPetResources(pet, bookcase);
        room.setBookcase(bookcase);
        petService.addExperience(pet, 1);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void moveBookcase(UserPetDetails userPetDetails, Point position)
            throws ServiceException {
        Room room = roomDao.findByPetId(userPetDetails.getPetId())
                .orElseThrow();
        if (room.getBookcase() == null) {
            throw new ServiceException("No bookcase in your room.");
        }
        room.setBookcaseX(position.x());
        room.setBookcaseY(position.y());
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void buildMachineWithDrinks(
            UserPetDetails userPetDetails, Point position)
            throws ServiceException {
        Pet pet = petDao.findByIdWithDrinksAndJournalEntriesAndBuildingMaterialsAndAchievements(
                userPetDetails.getPetId()).orElseThrow();
        Room room = roomDao.findByPetId(pet.getId())
                .orElseThrow();
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
                .findFullById(1).orElseThrow();
        petService.substractPetResources(pet, machineWithDrinks);
        room.setMachineWithDrinks(machineWithDrinks);
        room.setMachineWithDrinksX(position.x());
        room.setMachineWithDrinksY(position.y());
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
    @Transactional(rollbackFor = ServiceException.class)
    public void moveMachineWithDrinks(
            UserPetDetails userPetDetails, Point position)
            throws ServiceException {
        Room room = roomDao.findByPetId(userPetDetails.getPetId())
                .orElseThrow();
        if (room.getMachineWithDrinks() == null) {
            throw new ServiceException("No drink in your room.");
        }
        room.setMachineWithDrinksX(position.x());
        room.setMachineWithDrinksY(position.y());
    }

    @Override
    public RoomBuildMenuCosts getBuildMenuCosts(UserPetDetails userPetDetails)
            throws ServiceException {
        RoomBuildMenuCosts roomBuildMenuCosts = new RoomBuildMenuCosts();
        List<Map<BuildingMaterialId, Integer>> refrigeratorCosts = new ArrayList<Map<BuildingMaterialId, Integer>>();
        List<Refrigerator> refrigerators = refrigeratorDao.findAllFull();
        for (Refrigerator refrigerator : refrigerators) {
            Map<BuildingMaterialId, Integer> map = new HashMap<BuildingMaterialId, Integer>();
            for (Entry<BuildingMaterialId, RefrigeratorCost> entry : refrigerator
                    .getRefrigeratorCost().entrySet()) {
                map.put(entry.getKey(),
                        entry.getValue().getCost());
            }
            refrigeratorCosts.add(map);
        }
        roomBuildMenuCosts.setRefrigeratorCosts(refrigeratorCosts);

        List<Map<BuildingMaterialId, Integer>> bookcaseCosts = new ArrayList<Map<BuildingMaterialId, Integer>>();
        List<Bookcase> bookcases = bookcaseDao.findAllFull();
        for (Bookcase bookcase : bookcases) {
            Map<BuildingMaterialId, Integer> map = new HashMap<BuildingMaterialId, Integer>();
            for (Entry<BuildingMaterialId, BookcaseCost> entry : bookcase
                    .getBookcaseCost().entrySet()) {
                map.put(entry.getKey(),
                        entry.getValue().getCost());
            }
            bookcaseCosts.add(map);
        }
        roomBuildMenuCosts.setBookcaseCosts(bookcaseCosts);

        List<Map<BuildingMaterialId, Integer>> drinkCosts = new ArrayList<Map<BuildingMaterialId, Integer>>();
        List<MachineWithDrinks> machineWithDrinksList = machineWithDrinksDao.findAllFull();
        for (MachineWithDrinks machineWithDrinks : machineWithDrinksList) {
            Map<BuildingMaterialId, Integer> map = new HashMap<BuildingMaterialId, Integer>();
            for (Entry<BuildingMaterialId, MachineWithDrinksCost> entry : machineWithDrinks
                    .getMachineWithDrinksCost().entrySet()) {
                map.put(entry.getKey(),
                        entry.getValue().getCost());
            }
            drinkCosts.add(map);
        }
        roomBuildMenuCosts.setMachineWithDrinksCosts(drinkCosts);
        return roomBuildMenuCosts;

    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void upgradeMachineWithDrinks(UserPetDetails userPetDetails)
            throws ServiceException {
        Pet pet = petDao.findFullById(userPetDetails.getPetId())
                .orElseThrow();
        Room room = roomDao.findByPetId(pet.getId())
                .orElseThrow();
        if (room.getMachineWithDrinks() == null) {
            throw new ServiceException("No machine with drinks in your room.");
        }
        MachineWithDrinks machineWithDrinks = machineWithDrinksDao
                .findFullById(room.getMachineWithDrinks().getId() + 1)
                .orElseThrow();
        if (machineWithDrinks == null) {
            throw new ServiceException("Your machine with drinks level is max.");
        }
        petService.substractPetResources(pet, machineWithDrinks);
        room.setMachineWithDrinks(machineWithDrinks);
        petService.addExperience(pet, 1);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void pickJournalOnFloor(UserPetDetails userPetDetails)
            throws ServiceException {
        Room room = roomDao.findByPetId(userPetDetails.getPetId())
                .orElseThrow();
        if (room.isJournalOnFloor() == false)
            throw new ServiceException("There isn't any journal in your room.");
        room.setJournalOnFloor(false);
        Pet pet = petDao.findById(userPetDetails.getPetId())
                .orElseThrow();
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
    @Transactional(rollbackFor = ServiceException.class)
    public void journalClosed(UserPetDetails userPetDetails)
            throws ServiceException {
        long count =  petJournalEntryDao
                .countByPetIdAndJournalEntryCode(userPetDetails.getPetId(),
                        JournalEntryId.OPEN_NEWBIE_BOXES);
        if (count == 0) {
            Pet fullPet = petDao.findFullById(userPetDetails.getPetId())
                    .orElseThrow();
            PetJournalEntry newPetJournalEntry = new PetJournalEntry();
            newPetJournalEntry.setCreatedAt(OffsetDateTime.now(clock));
            newPetJournalEntry.setPet(fullPet);
            newPetJournalEntry.setJournalEntry(JournalEntryId.OPEN_NEWBIE_BOXES);
            newPetJournalEntry.setReaded(false);
            fullPet.getJournalEntries().put(
                    newPetJournalEntry.getJournalEntry(), newPetJournalEntry);
            petService.addExperience(fullPet, 1);
            
            Room room = roomDao.findByPetId(fullPet.getId())
                    .orElseThrow();
            room.setBoxNewbie1(true);
            room.setBoxNewbie2(true);
            room.setBoxNewbie3(true);
        }

    }
}
