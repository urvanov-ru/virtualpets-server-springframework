package ru.urvanov.virtualpets.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;

import ru.urvanov.virtualpets.server.controller.api.domain.GetPetBooksResult;
import ru.urvanov.virtualpets.server.dao.ClothDao;
import ru.urvanov.virtualpets.server.dao.LevelDao;
import ru.urvanov.virtualpets.server.dao.PetDao;
import ru.urvanov.virtualpets.server.dao.PetFoodDao;
import ru.urvanov.virtualpets.server.dao.PetJournalEntryDao;
import ru.urvanov.virtualpets.server.dao.RoomDao;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.AchievementId;
import ru.urvanov.virtualpets.server.dao.domain.Book;
import ru.urvanov.virtualpets.server.dao.domain.Bookcase;
import ru.urvanov.virtualpets.server.dao.domain.BookcaseCost;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId;
import ru.urvanov.virtualpets.server.dao.domain.LastCreatedPet;
import ru.urvanov.virtualpets.server.dao.domain.Level;
import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks;
import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinksCost;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetAchievement;
import ru.urvanov.virtualpets.server.dao.domain.PetBuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;
import ru.urvanov.virtualpets.server.dao.domain.RefrigeratorCost;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.NotEnoughPetResourcesException;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

/**
 * Тесты для {@link PetServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
class PetServiceImplJUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(PetServiceImplJUnitTest.class);

    private static final int LEVEL1_ID = 1;
    private static final int LEVEL1_EXPERIENCE = 0;
    private static final int LEVEL2_ID = 2;
    private static final int LEVEL2_EXPERIENCE = 10;

    private static final int START = 0;

    private static final int LIMIT = 10;

    private static final Integer PET_ID = 1;

    private static final OffsetDateTime PET_CREATED_DATE
            = OffsetDateTime.of(
                    LocalDate.of(2024, 8, 20),
                    LocalTime.of(10, 0),
                    ZoneOffset.ofHours(3));

    private static final String PET_FULL_NAME = null;

    private static final String BOOK1_ID = "PUSHKIN";

    private static final int BOOK1_BOOKCASE_LEVEL = 1;

    private static final int BOOK1_BOOKCASE_ORDER = 0;

    private static final float BOOK1_HIDDEN_OBJECTS_GAME_DROP_RATE = 0;

    private static final String BOOK2_ID = "SUPERBOOK";

    private static final int BOOK2_BOOKCASE_LEVEL = 1;

    private static final int BOOK2_BOOKCASE_ORDER = 0;

    private static final float BOOK2_HIDDEN_OBJECTS_GAME_DROP_RATE = 0;

    private static final Integer USER_ID = 1;
    
    @Mock
    private RoomDao roomDao;

    @Mock
    private PetDao petDao;

    @Mock
    private UserDao userDao;

    @Mock
    private PetFoodDao petFoodDao;

    @Mock
    private LevelDao levelDao;

    @Mock
    private ClothDao clothDao;

    @Mock
    private PetJournalEntryDao petJournalEntryDao;
    
    @Mock
    private ConversionService conversionService;

    private ZoneId zoneId = ZoneId.of("Europe/Moscow");

    @Spy
    private Clock clock = Clock.fixed(ZonedDateTime
            .of(LocalDate.of(2024, 8, 14), LocalTime.of(16, 58), zoneId)
            .toInstant(), zoneId);

    @InjectMocks
    private PetServiceImpl service;
    
    @BeforeAll
    static void beforeAll() {
        logger.info("Before all tests in the class.");
    }
    
    @AfterAll
    static void afterAll() {
        logger.info("After all tests in the class.");
    }
    
    @BeforeEach
    void beforeEach() {
        logger.info("This method runs before each test.");
    }
    
    @AfterEach
    void afterEach() {
        logger.info("This method runs after each test.");
    }
    
    @Test
    void testAddExperience_ok() {
        // Подготовка тестовых данных
        Pet pet = new Pet();
        pet.setExperience(0);
        Level level1 = new Level(LEVEL1_ID, LEVEL1_EXPERIENCE);
        Level level2 = new Level(LEVEL2_ID, LEVEL2_EXPERIENCE);
        pet.setLevel(level1);
        
        // Настройка mock-объектов
        when(levelDao.findById(LEVEL2_ID))
                .thenReturn(Optional.of(level2));
        
        // Вызов тестируемого метода
        service.addExperience(pet, 1);
        
        // Проверка результата
        assertEquals(1, pet.getExperience());
    }
    
    @Test
    void testAddExperience_levelUp() {
        // Подготовка тестовых данных
        Pet pet = new Pet();
        pet.setExperience(9);
        Level level1 = new Level(LEVEL1_ID, LEVEL1_EXPERIENCE);
        Level level2 = new Level(LEVEL2_ID, LEVEL2_EXPERIENCE);
        pet.setLevel(level1);
        
        // Настройка mock-объектов
        when(levelDao.findById(LEVEL2_ID)).thenReturn(Optional.of(level2));
        
        // Вызов тестируемого метода
        service.addExperience(pet, 1);
        
        // Проверка результата
        assertEquals(10, pet.getExperience());
        assertEquals(level2, pet.getLevel());
    }
    
    @Test
    void testAddExperience_lastLevel() {
        // Подготовка тестовых данных
        Pet pet = new Pet();
        pet.setExperience(10);
        Level level2 = new Level(LEVEL2_ID, LEVEL2_EXPERIENCE);
        pet.setLevel(level2);
        
        // Настройка mock-объектов
        when(levelDao.findById(LEVEL2_ID)).thenReturn(Optional.of(level2));
        when(levelDao.findById(LEVEL2_ID + 1)).thenReturn(Optional.empty());
        
        // Вызов тестируемого метода
        service.addExperience(pet, 1);
        
        // Проверка результата
        assertEquals(10, pet.getExperience());
        assertEquals(level2, pet.getLevel());
    }
    
    @Test
    void substractPetResources_refrigerator_ok()
            throws NotEnoughPetResourcesException {
        // Подготовка тестовых данных
        Pet pet = new Pet();
        Map<BuildingMaterialId, PetBuildingMaterial> petBuildingMaterials
                = new HashMap<>();
        pet.setBuildingMaterials(petBuildingMaterials);
        PetBuildingMaterial petBuildingMaterial = new PetBuildingMaterial();
        petBuildingMaterial.setBuildingMaterialCount(10);
        petBuildingMaterials.put(BuildingMaterialId.STONE,
                petBuildingMaterial);
        Map<BuildingMaterialId, RefrigeratorCost> refrigeratorCosts
                = new HashMap<>();
        BuildingMaterial buidingMaterialStone = new BuildingMaterial(
                BuildingMaterialId.STONE);
        Refrigerator refrigerator = new Refrigerator();
        RefrigeratorCost refrigeratorCost = new RefrigeratorCost(1,
                refrigerator, buidingMaterialStone, 2);
        refrigeratorCosts.put(BuildingMaterialId.STONE,
                refrigeratorCost);
        refrigerator.setRefrigeratorCosts(refrigeratorCosts);
        
        
        // Вызов тестируемого метода
        service.substractPetResources(pet, refrigerator);

        // Проверка результата
        assertEquals(8,
                pet.getBuildingMaterials().get(BuildingMaterialId.STONE)
                        .getBuildingMaterialCount());
    }
    
    @Test
    void substractPetResources_refrigerator_exception()
            throws NotEnoughPetResourcesException {
        // Подготовка тестовых данных
        Pet pet = new Pet();
        Map<BuildingMaterialId, PetBuildingMaterial> petBuildingMaterials
                = new HashMap<>();
        pet.setBuildingMaterials(petBuildingMaterials);
        PetBuildingMaterial petBuildingMaterial = new PetBuildingMaterial();
        petBuildingMaterial.setBuildingMaterialCount(1);
        petBuildingMaterials.put(BuildingMaterialId.STONE,
                petBuildingMaterial);
        Map<BuildingMaterialId, RefrigeratorCost> refrigeratorCosts
                = new HashMap<>();
        BuildingMaterial buidingMaterialStone = new BuildingMaterial(
                BuildingMaterialId.STONE);
        Refrigerator refrigerator = new Refrigerator();
        RefrigeratorCost refrigeratorCost = new RefrigeratorCost(1,
                refrigerator, buidingMaterialStone, 2);
        refrigeratorCosts.put(BuildingMaterialId.STONE,
                refrigeratorCost);
        refrigerator.setRefrigeratorCosts(refrigeratorCosts);

        // Вызов тестируемого метода и проверка результата
        assertThrows(NotEnoughPetResourcesException.class, () ->
                service.substractPetResources(pet, refrigerator));
    }
    
    @Test
    void substractPetResources_machineWithDrinks_ok()
            throws NotEnoughPetResourcesException {
        // Подготовка тестовых данных
        Pet pet = new Pet();
        Map<BuildingMaterialId, PetBuildingMaterial> petBuildingMaterials
                = new HashMap<>();
        pet.setBuildingMaterials(petBuildingMaterials);
        PetBuildingMaterial petBuildingMaterial = new PetBuildingMaterial();
        petBuildingMaterial.setBuildingMaterialCount(10);
        petBuildingMaterials.put(BuildingMaterialId.STONE,
                petBuildingMaterial);
        Map<BuildingMaterialId, MachineWithDrinksCost> machineWithDrinksCosts
                = new HashMap<>();
        BuildingMaterial buidingMaterialStone = new BuildingMaterial(
                BuildingMaterialId.STONE);
        MachineWithDrinks machineWithDrinks = new MachineWithDrinks();
        MachineWithDrinksCost machineWithDrinksCost = new MachineWithDrinksCost(1,
                machineWithDrinks, buidingMaterialStone, 2);
        machineWithDrinksCosts.put(BuildingMaterialId.STONE,
                machineWithDrinksCost);
        machineWithDrinks.setMachineWithDrinksCosts(machineWithDrinksCosts);

        // Вызов тестируемого метода
        service.substractPetResources(pet, machineWithDrinks);

        // Проверка результата
        assertEquals(8,
                pet.getBuildingMaterials().get(BuildingMaterialId.STONE)
                        .getBuildingMaterialCount());
    }
    
    @Test
    void substractPetResources_Bookcase_ok()
            throws NotEnoughPetResourcesException {
        // Подготовка тестовых данных
        Pet pet = new Pet();
        Map<BuildingMaterialId, PetBuildingMaterial> petBuildingMaterials
                = new HashMap<>();
        pet.setBuildingMaterials(petBuildingMaterials);
        PetBuildingMaterial petBuildingMaterial = new PetBuildingMaterial();
        petBuildingMaterial.setBuildingMaterialCount(10);
        petBuildingMaterials.put(BuildingMaterialId.STONE,
                petBuildingMaterial);
        Map<BuildingMaterialId, BookcaseCost> bookcaseCosts
                = new HashMap<>();
        BuildingMaterial buidingMaterialStone = new BuildingMaterial(
                BuildingMaterialId.STONE);
        Bookcase bookcase = new Bookcase();
        BookcaseCost bookcaseCost = new BookcaseCost(1,
                bookcase, buidingMaterialStone, 2);
        bookcaseCosts.put(BuildingMaterialId.STONE,
                bookcaseCost);
        bookcase.setBookcaseCosts(bookcaseCosts);

        // Вызов тестируемого метода
        service.substractPetResources(pet, bookcase);

        // Проверка результата
        assertEquals(8,
                pet.getBuildingMaterials().get(BuildingMaterialId.STONE)
                        .getBuildingMaterialCount());
    }
    
    @Test
    void getPetJournalEntriesCount_ok() {
        // Подготовка mock-объектов
        when(petDao.getPetNewJournalEntriesCount(eq(10))).thenReturn(2L);
        
        // Вызов тестируемого метода 
        Long actual = service.getPetNewJournalEntriesCount(10);
        
        // Проверка результата
        assertEquals(Long.valueOf(2L), actual);
    }
    
    @Test
    void calculateAchievements_ok() {
        // Подготовка тестовых данных
        Pet pet = new Pet();
        Map<AchievementId, PetAchievement> achievements = new HashMap<>();
        PetAchievement petAchievement = new PetAchievement();
        petAchievement.setAchievement(AchievementId.BUILD_1);
        petAchievement.setWasShown(false);
        achievements.put(AchievementId.BUILD_1, petAchievement);
        pet.setAchievements(achievements);
        
        // Вызов тестируемого метода
        List<AchievementId> actual = service.calculateAchievements(pet);
        
        // Проверка результата
        assertEquals(1, actual.size());
        assertTrue(actual.contains(AchievementId.BUILD_1));
    }
    
    @Test
    void findLastCreatedPets() {
        // Подготовка тестовых данных
        Pet pet = new Pet();
        pet.setId(PET_ID);
        pet.setCreatedDate(PET_CREATED_DATE);
        pet.setName(PET_FULL_NAME);
        List<Pet> petList = List.of(pet);
        
        // Подготовка mock-объектов
        when(petDao.findLastCreatedPets(START, LIMIT))
                .thenReturn(petList);
        
        // Вызов тестируемого метода
        List<LastCreatedPet> actual = service.findLastCreatedPets(
                START, LIMIT);
        
        // Проверка результата
        assertFalse(actual.isEmpty());
        assertEquals(1, actual.size());
        LastCreatedPet actualPet = actual.get(0);
        assertEquals(pet.getId(), actualPet.getId());
        assertEquals(pet.getCreatedDate().toInstant(),
                actualPet.getCreatedDate().toInstant());
        assertEquals(pet.getName(), actualPet.getName());
    }
    
    @Test
    void getPetBooks() throws ServiceException {
        // Подготовка тестовых данных
        Pet pet = new Pet();
        pet.setId(PET_ID);
        Set<Book> books = new HashSet<>();
        Book book1 = new Book(BOOK1_ID, BOOK1_BOOKCASE_LEVEL,
                BOOK1_BOOKCASE_ORDER,
                BOOK1_HIDDEN_OBJECTS_GAME_DROP_RATE);
        books.add(book1);
        Book book2 = new Book(BOOK2_ID, BOOK2_BOOKCASE_LEVEL,
                BOOK2_BOOKCASE_ORDER,
                BOOK2_HIDDEN_OBJECTS_GAME_DROP_RATE);
        books.add(book2);
        pet.setBooks(books );
        UserPetDetails userPetDetails = new UserPetDetails(
                USER_ID, PET_ID);
        
        // Настройка mock-объектов
        when(petDao.findByIdWithFullBooks(PET_ID))
                .thenReturn(Optional.of(pet));
        ru.urvanov.virtualpets.server.controller.api.domain.Book actualBook1
                = new ru.urvanov.virtualpets.server.controller.api.domain.Book(
                        BOOK1_ID,
                        BOOK1_BOOKCASE_LEVEL, 
                        BOOK1_BOOKCASE_ORDER);
        ru.urvanov.virtualpets.server.controller.api.domain.Book actualBook2
                = new ru.urvanov.virtualpets.server.controller.api.domain.Book(
                        BOOK2_ID,
                        BOOK2_BOOKCASE_LEVEL, 
                        BOOK2_BOOKCASE_ORDER);
        when(conversionService.convert(book1,
                ru.urvanov.virtualpets.server.controller.api.domain.Book.class))
                        .thenReturn(actualBook1);
        when(conversionService.convert(book2,
                ru.urvanov.virtualpets.server.controller.api.domain.Book.class))
                        .thenReturn(actualBook2);
        
        // Вызов тестируемого сервиса
        GetPetBooksResult actual = service.getPetBooks(userPetDetails);
        
        // Проверка результата
        assertNotNull(actual);
        assertNotNull(actual.books());
        assertEquals(books.size(), actual.books().size());
        assertEquals(BOOK1_ID, actual.books().get(0).id());
    }

}
