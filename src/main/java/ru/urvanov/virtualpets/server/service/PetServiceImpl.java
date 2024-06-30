package ru.urvanov.virtualpets.server.service;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ru.urvanov.virtualpets.server.api.domain.CreatePetArg;
import ru.urvanov.virtualpets.server.api.domain.DrinkArg;
import ru.urvanov.virtualpets.server.api.domain.GetPetBooksResult;
import ru.urvanov.virtualpets.server.api.domain.GetPetClothsResult;
import ru.urvanov.virtualpets.server.api.domain.GetPetDrinksResult;
import ru.urvanov.virtualpets.server.api.domain.GetPetFoodsResult;
import ru.urvanov.virtualpets.server.api.domain.GetPetJournalEntriesResult;
import ru.urvanov.virtualpets.server.api.domain.GetPetRucksackInnerResult;
import ru.urvanov.virtualpets.server.api.domain.PetInfo;
import ru.urvanov.virtualpets.server.api.domain.PetListResult;
import ru.urvanov.virtualpets.server.api.domain.SatietyArg;
import ru.urvanov.virtualpets.server.api.domain.SavePetCloths;
import ru.urvanov.virtualpets.server.api.domain.SelectPetArg;
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
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId;
import ru.urvanov.virtualpets.server.dao.domain.Cloth;
import ru.urvanov.virtualpets.server.dao.domain.DrinkId;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryId;
import ru.urvanov.virtualpets.server.dao.domain.Level;
import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks;
import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinksCost;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetAchievement;
import ru.urvanov.virtualpets.server.dao.domain.PetBuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.PetDrink;
import ru.urvanov.virtualpets.server.dao.domain.PetFood;
import ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry;
import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;
import ru.urvanov.virtualpets.server.dao.domain.RefrigeratorCost;
import ru.urvanov.virtualpets.server.dao.domain.Room;
import ru.urvanov.virtualpets.server.dao.domain.SelectedPet;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.dao.exception.DaoException;
import ru.urvanov.virtualpets.server.service.domain.PetDetails;
import ru.urvanov.virtualpets.server.service.domain.PetInformationPageAchievement;
import ru.urvanov.virtualpets.server.service.exception.NotEnoughPetResourcesException;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@Service("petService")
public class PetServiceImpl implements PetService, PetApiService {

    @Autowired
    private RoomDao roomDao;
    
    @Autowired
    private PetDao petDao;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private PetFoodDao petFoodDao;
    
    @Autowired
    private LevelDao levelDao;
    
    @Autowired
    private ClothDao clothDao;
    
    @Autowired
    private PetJournalEntryDao petJournalEntryDao;
    
    @Autowired
    private ConversionService conversionService;

    @Autowired
    private Clock clock;
    
    @Override
    public void addExperience(Pet pet, Integer exp) {
        int nextExperience = pet.getExperience() + exp;
        Level nextLevel = levelDao.findById(pet.getLevel().getId() + 1);
        if (nextLevel == null) {
            Level lastLevel = levelDao.findById(pet.getLevel().getId());
            pet.setExperience(Math.min(nextExperience, lastLevel.getExperience()));
        } else {
            pet.setExperience(nextExperience);
            if (nextExperience >= nextLevel.getExperience()) {
                pet.setLevel(nextLevel);
            }
        }
    }
    
    @Override
    public void substractPetResources(Pet fullPet, Refrigerator refrigerator) throws NotEnoughPetResourcesException {
        Map<BuildingMaterialId, PetBuildingMaterial> petBuildingMaterials = fullPet.getBuildingMaterials();
        Map<BuildingMaterialId, RefrigeratorCost> resourceCosts =  refrigerator.getRefrigeratorCost();
        for (Entry<BuildingMaterialId, RefrigeratorCost> entry : resourceCosts.entrySet()) {
            BuildingMaterialId buildingMaterialType = entry.getKey();
            RefrigeratorCost resourceCost = entry.getValue();
            PetBuildingMaterial petBuildingMaterial = petBuildingMaterials.get(buildingMaterialType);
            if (petBuildingMaterial == null) {
                throw new NotEnoughPetResourcesException();
            } else {
                int newCount = petBuildingMaterial.getBuildingMaterialCount() - resourceCost.getCost();
                if (newCount < 0) {
                    throw new NotEnoughPetResourcesException();
                }
                petBuildingMaterial.setBuildingMaterialCount(newCount);
            }
        }
    }
    
    @Override
    public void substractPetResources(Pet fullPet, Bookcase bookcase) throws NotEnoughPetResourcesException {
        Map<BuildingMaterialId, PetBuildingMaterial> petBuildingMaterials = fullPet.getBuildingMaterials();
        Map<BuildingMaterialId, BookcaseCost> resourceCosts =  bookcase.getBookcaseCost();
        for (Entry<BuildingMaterialId, BookcaseCost> entry : resourceCosts.entrySet()) {
            BuildingMaterialId buildingMaterialType = entry.getKey();
            BookcaseCost resourceCost = entry.getValue();
            PetBuildingMaterial petBuildingMaterial = petBuildingMaterials.get(buildingMaterialType);
            if (petBuildingMaterial == null) {
                throw new NotEnoughPetResourcesException();
            } else {
                int newCount = petBuildingMaterial.getBuildingMaterialCount() - resourceCost.getCost();
                if (newCount < 0) {
                    throw new NotEnoughPetResourcesException();
                }
                petBuildingMaterial.setBuildingMaterialCount(newCount);
            }
        }
    }
    
    @Override
    public void substractPetResources(Pet fullPet, MachineWithDrinks drink) throws NotEnoughPetResourcesException {
        Map<BuildingMaterialId, PetBuildingMaterial> petBuildingMaterials = fullPet.getBuildingMaterials();
        Map<BuildingMaterialId, MachineWithDrinksCost> resourceCosts =  drink.getMachineWithDrinksCost();
        for (Entry<BuildingMaterialId, MachineWithDrinksCost> entry : resourceCosts.entrySet()) {
            BuildingMaterialId buildingMaterialType = entry.getKey();
            MachineWithDrinksCost resourceCost = entry.getValue();
            PetBuildingMaterial petBuildingMaterial = petBuildingMaterials.get(buildingMaterialType);
            if (petBuildingMaterial == null) {
                throw new NotEnoughPetResourcesException();
            } else {
                int newCount = petBuildingMaterial.getBuildingMaterialCount() - resourceCost.getCost();
                if (newCount < 0) {
                    throw new NotEnoughPetResourcesException();
                }
                petBuildingMaterial.setBuildingMaterialCount(newCount);
            }
        }
    }

    @Override
    public Long getPetNewJournalEntriesCount(Integer petId) {
        return petDao.getPetNewJournalEntriesCount(petId);
    }

    @Override
    public List<AchievementId> calculateAchievements(Pet fullPet) {
        List<AchievementId> result = new ArrayList<AchievementId>();
        Map<AchievementId, PetAchievement> map = fullPet.getAchievements();
        for (PetAchievement pa : map.values()) {
            if (!pa.getWasShown()) {
                pa.setWasShown(true);
                result.add(pa.getAchievement());
            }
        }
        return result;
    }

    @Override
    public List<Pet> findLastCreatedPets(int start, int limit) {
        return petDao.findLastCreatedPets(start, limit);
    }
    
    @Override
    public GetPetBooksResult getPetBooks() throws DaoException,
            ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet", ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findByIdWithFullBooks(selectedPet.getId());
        Set<Book> books = pet.getBooks();
        
        List<ru.urvanov.virtualpets.server.api.domain.Book> resultBooks = books.stream()
                .map(b -> new ru.urvanov.virtualpets.server.api.domain.Book(b.getId(), b.getBookcaseLevel(), b.getBookcaseOrder()))
                .collect(Collectors.toList());
        
        return new GetPetBooksResult(resultBooks);
    }
    

    @Override
    public GetPetClothsResult getPetCloths() {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet", ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findByIdWithFullCloths(selectedPet.getId());
        Set<Cloth> cloths = pet.getCloths();
        
        List<ru.urvanov.virtualpets.server.api.domain.Cloth> resultCloths = cloths.stream()
                .map(c -> new ru.urvanov.virtualpets.server.api.domain.Cloth(
                        c.getId(),
                        c.getClothType(),
                        c.getWardrobeOrder()
                        ))
                .collect(Collectors.toList());

        
        ru.urvanov.virtualpets.server.api.domain.GetPetClothsResult result = new ru.urvanov.virtualpets.server.api.domain.GetPetClothsResult(
                Optional.of(pet).map(Pet::getHat).map(Cloth::getId).orElse(null),
                Optional.of(pet).map(Pet::getCloth).map(Cloth::getId).orElse(null),
                Optional.of(pet).map(Pet::getBow).map(Cloth::getId).orElse(null),
                resultCloths);
        return result;
    }
    
    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
    public void savePetCloths(SavePetCloths saveClothArg) throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet", ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findById(selectedPet.getId());
        Cloth hat = null;
        if (saveClothArg.hatId() != null) {
            // Получаем прокси сущности Cloth
            // без лишнего обращения к базе данных.
            hat = clothDao.getReference(saveClothArg.hatId());
        }
        Cloth cloth = null;
        if (saveClothArg.clothId() != null) {
            // Получаем прокси сущности Cloth
            // без лишнего обращения к базе данных.
            cloth = clothDao.getReference(saveClothArg.clothId());
        }
        Cloth bow = null;
        if (saveClothArg.bowId() != null) {
            // Получаем прокси сущности Cloth
            // без лишнего обращения к базе данных.
            bow = clothDao.getReference(saveClothArg.bowId());
        }
        pet.setHat(hat);
        pet.setCloth(cloth);
        pet.setBow(bow);
    }
    
    @Override
    public GetPetDrinksResult getPetDrinks() throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet", ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findByIdWithFullDrinks(selectedPet.getId());
        Map<DrinkId, PetDrink> drinks = pet.getDrinks();
        List<ru.urvanov.virtualpets.server.api.domain.Drink> resultDrinks = drinks.values().stream()
                .map(d -> new ru.urvanov.virtualpets.server.api.domain.Drink(
                        d.getDrink().getId(),
                        d.getDrink().getMachineWithDrinksLevel(),
                        d.getDrink().getMachineWithDrinksOrder(),
                        d.getDrinkCount()))
                .collect(Collectors.toList());
        
        return new GetPetDrinksResult(resultDrinks);
    }

    @Override
    public GetPetFoodsResult getPetFoods() throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet", ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findByIdWithFullFoods(selectedPet.getId());
        List<ru.urvanov.virtualpets.server.api.domain.Food> resultFoods = pet.getFoods().values().stream()
                .map(f -> new ru.urvanov.virtualpets.server.api.domain.Food(
                        f.getFood().getId(),
                        f.getFood().getRefrigeratorLevel(),
                        f.getFood().getRefrigeratorOrder(),
                        f.getFoodCount()
                        ))
                .collect(Collectors.toList());
        return new GetPetFoodsResult(resultFoods);
    }

    @Override
    @Transactional(rollbackFor = {ServiceException.class, DaoException.class})
    public GetPetJournalEntriesResult getPetJournalEntries(int count) throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet", ServletRequestAttributes.SCOPE_SESSION);
        List<ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry> serverPetJournalEntries = petJournalEntryDao.findLastByPetId(selectedPet.getId(), count);
        List<ru.urvanov.virtualpets.server.api.domain.PetJournalEntry> apiEntries = new ArrayList<>(serverPetJournalEntries.size());
        for (int n = 0; n < serverPetJournalEntries.size(); n++) {
            ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry serverPetJournalEntry = serverPetJournalEntries.get(n);
            if (!serverPetJournalEntry.isReaded()) {
                serverPetJournalEntry.setReaded(true);
            }
            ru.urvanov.virtualpets.server.api.domain.PetJournalEntry apiEntry = new ru.urvanov.virtualpets.server.api.domain.PetJournalEntry(serverPetJournalEntry.getCreatedAt(), serverPetJournalEntry.getJournalEntry());
            apiEntries.add(apiEntry);
        }
        return new GetPetJournalEntriesResult(apiEntries);
    }
    
    @Override
    public PetListResult getUserPets() throws DaoException,
            ServiceException {
        org.springframework.web.context.request.ServletRequestAttributes sra = (org.springframework.web.context.request.ServletRequestAttributes) org.springframework.web.context.request.RequestContextHolder
                .getRequestAttributes();
        Object var1 = sra.getAttribute("my1",
                ServletRequestAttributes.SCOPE_SESSION);
        System.out.println("VAR1=" + var1);
        SecurityContext securityContext = (SecurityContext) SecurityContextHolder
                .getContext();
        Authentication authentication = securityContext.getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<Pet> pets = petDao.findByUserId(user.getId());

        List<PetInfo> petInfos = pets.stream().map(p -> new PetInfo(p.getId(), p.getName(), p.getPetType())).collect(Collectors.toList());
        return new PetListResult(petInfos);
    }

    @Override
    @Transactional(rollbackFor = {ServiceException.class, DaoException.class})
    public void create(CreatePetArg arg) throws DaoException,
            ServiceException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User user = (User) authentication.getPrincipal();
        Pet pet = new Pet();
        pet.setName(arg.name());
        pet.setCreatedDate(OffsetDateTime.now(clock));
        pet.setUser(userDao.getReference(user.getId()));
        pet.setComment(arg.comment());
        pet.setPetType(arg.petType());
        Level level = levelDao.findById(1);
        pet.setLevel(level);
    }

    @Override
    @Transactional(rollbackFor = {ServiceException.class, DaoException.class})
    public void select(SelectPetArg arg) throws DaoException,
            ServiceException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User user = (User) authentication.getPrincipal();
        Integer userId = user.getId();

        int id = arg.petId();
        Pet pet = petDao.findFullById(id);
        OffsetDateTime currentDateTime = OffsetDateTime.now(clock);
        boolean fireAchievement = false;
        if (pet.getEveryDayLoginLast() == null) {
            fireAchievement = true;
        } else if (pet.getEveryDayLoginLast().plusDays(1).minusHours(6).compareTo(currentDateTime) < 0) {
            if (pet.getEveryDayLoginLast().plusDays(2).compareTo(currentDateTime) > 0) {
                fireAchievement = true;
            } else {
                pet.setEveryDayLoginCount(0);
                pet.setEveryDayLoginLast(currentDateTime);
            }
        }
        
        if (fireAchievement) {
            pet.setEveryDayLoginCount(pet.getEveryDayLoginCount() + 1);
            pet.setEveryDayLoginLast(currentDateTime);
            this.addAchievementIfNot(pet,
                    AchievementId.valueOf("EVERY_DAY_LOGIN_" + pet.getEveryDayLoginCount()));
        }
        
        pet.setLoginDate(currentDateTime);
        if (!pet.getUser().getId().equals(userId)) {
            throw new ServiceException();
        }
    }

    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
    public void drink(DrinkArg drinkArg) throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findByIdWithDrinksAndJournalEntriesAndAchievements(selectedPet.getId());
        DrinkId drinkType =  conversionService.convert(drinkArg.drinkId(), DrinkId.class);
        Map<DrinkId, PetDrink> drinks = pet.getDrinks();
        PetDrink petDrink = drinks.get(drinkType);
        petDrink.setDrinkCount(petDrink.getDrinkCount() - 1);
        pet.setDrink(100);
        if (pet.getJournalEntries().get(JournalEntryId.BUILD_REFRIGERATOR) == null) {
            PetJournalEntry newPetJournalEntry = new PetJournalEntry();
            newPetJournalEntry.setCreatedAt(OffsetDateTime.now(clock));
            newPetJournalEntry.setPet(pet);
            newPetJournalEntry.setJournalEntry(JournalEntryId.BUILD_REFRIGERATOR);
            newPetJournalEntry.setReaded(false);
            pet.getJournalEntries().put(newPetJournalEntry.getJournalEntry(), newPetJournalEntry);
        }
        sra.setAttribute("pet", new SelectedPet(pet), ServletRequestAttributes.SCOPE_SESSION);
        if (pet.getDrinkCount() < Integer.MAX_VALUE) pet.setDrinkCount(pet.getDrinkCount() + 1);
        if (pet.getDrinkCount() == 1) addAchievementIfNot(pet, AchievementId.DRINK_1);
        if (pet.getDrinkCount() == 10) addAchievementIfNot(pet, AchievementId.DRINK_10);
        if (pet.getDrinkCount() == 100) addAchievementIfNot(pet, AchievementId.DRINK_100);
        addExperience(pet, 1);
    }

    public void addAchievementIfNot(Pet pet, AchievementId achievement) {
        if (!pet.getAchievements().containsKey(achievement)) {
            PetAchievement petAchievement = new PetAchievement();
            petAchievement.setPet(pet);
            petAchievement.setAchievement(achievement);
            pet.getAchievements().put(achievement, petAchievement);
        }
    }

    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
    public void satiety(SatietyArg satietyArg) throws DaoException,
            ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);

        Pet pet = petDao.findByIdWithFoodsJournalEntriesAndAchievements(selectedPet.getId());
        PetFood food = petFoodDao.findByPetIdAndFoodType(pet.getId(), satietyArg.foodId());
        if (food == null) {
            throw new ServiceException("Food count = 0.");
        } else {
            if (food.getFoodCount() == 0) {
                throw new ServiceException("Food count = 0.");
            }
            food.setFoodCount(food.getFoodCount() - 1);
        }
        pet.setSatiety(100);
        if (pet.getJournalEntries().get(JournalEntryId.BUILD_BOOKCASE) == null) {
            PetJournalEntry newPetJournalEntry = new PetJournalEntry();
            newPetJournalEntry.setCreatedAt(OffsetDateTime.now(clock));
            newPetJournalEntry.setPet(pet);
            newPetJournalEntry.setJournalEntry(JournalEntryId.BUILD_BOOKCASE);
            newPetJournalEntry.setReaded(false);
            pet.getJournalEntries().put(newPetJournalEntry.getJournalEntry(), newPetJournalEntry);
        }
        sra.setAttribute("pet", new SelectedPet(pet), ServletRequestAttributes.SCOPE_SESSION);
        if (pet.getEatCount() < Integer.MAX_VALUE) pet.setEatCount(pet.getEatCount() + 1);
        if (pet.getEatCount() == 1) addAchievementIfNot(pet, AchievementId.FEED_1);
        if (pet.getEatCount() == 10) addAchievementIfNot(pet, AchievementId.FEED_10);
        if (pet.getEatCount() == 100) addAchievementIfNot(pet, AchievementId.FEED_100);
        addExperience(pet, 1);
    }

    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
    public void education() throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet)  sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findByIdWithJournalEntriesAndAchievements(selectedPet.getId());
        pet.setEducation(100);
        
        if (pet.getJournalEntries().get(JournalEntryId.LEAVE_ROOM) == null) {
            PetJournalEntry newPetJournalEntry = new PetJournalEntry();
            newPetJournalEntry.setCreatedAt(OffsetDateTime.now(clock));
            newPetJournalEntry.setPet(pet);
            newPetJournalEntry.setJournalEntry(JournalEntryId.LEAVE_ROOM);
            newPetJournalEntry.setReaded(false);
            pet.getJournalEntries().put(newPetJournalEntry.getJournalEntry(), newPetJournalEntry);
        }
        
        sra.setAttribute("pet", new SelectedPet(pet), ServletRequestAttributes.SCOPE_SESSION);
        if (pet.getTeachCount() < Integer.MAX_VALUE) pet.setTeachCount(pet.getTeachCount() + 1);
        if (pet.getTeachCount() == 1) addAchievementIfNot(pet, AchievementId.TEACH_1);
        if (pet.getTeachCount() == 10) addAchievementIfNot(pet, AchievementId.TEACH_10);
        if (pet.getTeachCount() == 100) addAchievementIfNot(pet, AchievementId.TEACH_100);
        addExperience(pet, 1);
    }

    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
    public void mood() throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        Pet pet = (Pet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        pet = petDao.findById(pet.getId());
        pet.setMood(100);
        sra.setAttribute("pet", new SelectedPet(pet), ServletRequestAttributes.SCOPE_SESSION);
        addExperience(petDao.findById(pet.getId()), 1);
    }
    
    @Override
    public GetPetRucksackInnerResult getPetRucksackInner()
            throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet", ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findByIdWithFullBuildingMaterials(selectedPet.getId());
        Map<BuildingMaterialId, PetBuildingMaterial> buildingMaterials = pet.getBuildingMaterials();
        
        Map<BuildingMaterialId, Integer> buildingMaterialCounts = buildingMaterials.entrySet().stream()
                .<Entry<BuildingMaterialId, Integer>>map(e -> Map.entry(
                        e.getKey(),
                        e.getValue().getBuildingMaterialCount()))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        
        return new GetPetRucksackInnerResult(buildingMaterialCounts);
    }

    @Override
    public PetDetails petInformationPage(Integer id) {
        Pet fullPet = petDao.findFullById(id);
        PetDetails result = new PetDetails();
        result.setId(fullPet.getId());
        result.setName(fullPet.getName());
        result.setLevel(fullPet.getLevel().getId());
        List<PetInformationPageAchievement> achievements = new ArrayList<>();
        result.setAchievements(achievements);
        for (AchievementId achievement : AchievementId.values()) {
            PetInformationPageAchievement petInformationPageAchievement = new PetInformationPageAchievement();
            petInformationPageAchievement.setCode(achievement.name());
            petInformationPageAchievement.setUnlocked(fullPet.getAchievements().containsKey(achievement));
            achievements.add(petInformationPageAchievement);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
    public void delete(Integer petId) {
        Room room = roomDao.findByPetId(petId);
        if (room != null) {
            roomDao.delete(room);
        }
        petDao.delete(petId);
    }
    
    @Override
    public void updatePetsTask() {
        petDao.updatePetsTask();
    }
}
