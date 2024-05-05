package ru.urvanov.virtualpets.server.service;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import ru.urvanov.virtualpets.server.dao.domain.FoodId;
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
import ru.urvanov.virtualpets.server.service.domain.PetDetails;
import ru.urvanov.virtualpets.server.service.domain.PetInformationPageAchievement;
import ru.urvanov.virtualpets.server.service.exception.NotEnoughPetResourcesException;
import ru.urvanov.virtualpets.shared.domain.CreatePetArg;
import ru.urvanov.virtualpets.shared.domain.CreatePetResult;
import ru.urvanov.virtualpets.shared.domain.DrinkArg;
import ru.urvanov.virtualpets.shared.domain.GetPetBooksResult;
import ru.urvanov.virtualpets.shared.domain.GetPetClothsResult;
import ru.urvanov.virtualpets.shared.domain.GetPetDrinksResult;
import ru.urvanov.virtualpets.shared.domain.GetPetFoodsResult;
import ru.urvanov.virtualpets.shared.domain.GetPetJournalEntriesResult;
import ru.urvanov.virtualpets.shared.domain.GetPetRucksackInnerResult;
import ru.urvanov.virtualpets.shared.domain.PetInfo;
import ru.urvanov.virtualpets.shared.domain.PetListResult;
import ru.urvanov.virtualpets.shared.domain.SatietyArg;
import ru.urvanov.virtualpets.shared.domain.SavePetCloths;
import ru.urvanov.virtualpets.shared.domain.SelectPetArg;
import ru.urvanov.virtualpets.shared.domain.SelectPetResult;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;

@Service("petService")
public class PetServiceImpl implements PetService, ru.urvanov.virtualpets.shared.service.PetService {

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

    public PetDao getPetDao() {
        return petDao;
    }

    public void setPetDao(PetDao petDao) {
        this.petDao = petDao;
    }

    public PetFoodDao getPetFoodDao() {
        return petFoodDao;
    }

    public void setPetFoodDao(PetFoodDao petFoodDao) {
        this.petFoodDao = petFoodDao;
    }

    public ClothDao getClothDao() {
        return clothDao;
    }

    public void setClothDao(ClothDao clothDao) {
        this.clothDao = clothDao;
    }

    public ConversionService getConversionService() {
        return conversionService;
    }

    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public LevelDao getLevelDao() {
        return levelDao;
    }

    public void setLevelDao(LevelDao levelDao) {
        this.levelDao = levelDao;
    }

    @Override
    public void updatePetsTask() {
        petDao.updatePetsTask();
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
        
        List<ru.urvanov.virtualpets.shared.domain.Book> resultBooks = books.stream()
                .map(b -> new ru.urvanov.virtualpets.shared.domain.Book(b.getId(), b.getBookcaseLevel(), b.getBookcaseOrder()))
                .collect(Collectors.toList());
        
        GetPetBooksResult result = new GetPetBooksResult();
        result.setBooks(resultBooks);
        return result;
    }
    

    @Override
    public GetPetClothsResult getPetCloths() {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet", ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findByIdWithFullCloths(selectedPet.getId());
        Set<Cloth> cloths = pet.getCloths();
        
        List<ru.urvanov.virtualpets.shared.domain.Cloth> resultCloths = cloths.stream()
                .map(c -> new ru.urvanov.virtualpets.shared.domain.Cloth(
                        c.getId(),
                        conversionService.convert(c.getClothType(), ru.urvanov.virtualpets.shared.domain.ClothType.class),
                        c.getWardrobeOrder()
                        ))
                .collect(Collectors.toList());

        ru.urvanov.virtualpets.shared.domain.GetPetClothsResult result = new ru.urvanov.virtualpets.shared.domain.GetPetClothsResult();
        result.setCloths(resultCloths);
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
        return result;
    }
    
    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
    public void savePetCloths(SavePetCloths saveClothArg) throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet", ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findById(selectedPet.getId());
        Cloth hat = null;
        if (saveClothArg.getHatId() != null) {
            // Получаем прокси сущности Cloth
            // без лишнего обращения к базе данных.
            hat = clothDao.getReference(saveClothArg.getHatId());
        }
        Cloth cloth = null;
        if (saveClothArg.getClothId() != null) {
            // Получаем прокси сущности Cloth
            // без лишнего обращения к базе данных.
            cloth = clothDao.getReference(saveClothArg.getClothId());
        }
        Cloth bow = null;
        if (saveClothArg.getBowId() != null) {
            // Получаем прокси сущности Cloth
            // без лишнего обращения к базе данных.
            bow = clothDao.getReference(saveClothArg.getBowId());
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
        List<ru.urvanov.virtualpets.shared.domain.Drink> resultDrinks = drinks.values().stream()
                .map(d -> new ru.urvanov.virtualpets.shared.domain.Drink(
                        conversionService.convert(
                                d.getDrink().getId(),
                                ru.urvanov.virtualpets.shared.domain.DrinkType.class),
                        d.getDrink().getMachineWithDrinksLevel(),
                        d.getDrink().getMachineWithDrinksOrder(),
                        d.getDrinkCount()))
                .collect(Collectors.toList());
        
        ru.urvanov.virtualpets.shared.domain.GetPetDrinksResult result = new ru.urvanov.virtualpets.shared.domain.GetPetDrinksResult();
        result.setDrinks(resultDrinks);
        return result;
    }

    @Override
    public GetPetFoodsResult getPetFoods() throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet", ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findByIdWithFullFoods(selectedPet.getId());
        List<ru.urvanov.virtualpets.shared.domain.Food> resultFoods = pet.getFoods().values().stream()
                .map(f -> new ru.urvanov.virtualpets.shared.domain.Food(
                        conversionService.convert(
                                f.getFood().getId(),
                                ru.urvanov.virtualpets.shared.domain.FoodType.class),
                        f.getFood().getRefrigeratorLevel(),
                        f.getFood().getRefrigeratorOrder(),
                        f.getFoodCount()
                        ))
                .collect(Collectors.toList());
        ru.urvanov.virtualpets.shared.domain.GetPetFoodsResult result = new ru.urvanov.virtualpets.shared.domain.GetPetFoodsResult();
        result.setFoods(resultFoods);
        return result;
    }

    @Override
    public GetPetJournalEntriesResult getPetJournalEntries(int count) throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet", ServletRequestAttributes.SCOPE_SESSION);
        List<ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry> serverPetJournalEntries = petJournalEntryDao.findLastByPetId(selectedPet.getId(), count);
        GetPetJournalEntriesResult result = new GetPetJournalEntriesResult();
        ru.urvanov.virtualpets.shared.domain.PetJournalEntry[] sharedEntries = new ru.urvanov.virtualpets.shared.domain.PetJournalEntry[serverPetJournalEntries.size()];
        for (int n = 0; n < serverPetJournalEntries.size(); n++) {
            ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry serverPetJournalEntry = serverPetJournalEntries.get(n);
            serverPetJournalEntry.setReaded(true);
            petJournalEntryDao.save(serverPetJournalEntry);
            int sharedIndex = serverPetJournalEntries.size() - 1 - n;
            sharedEntries[sharedIndex] = new ru.urvanov.virtualpets.shared.domain.PetJournalEntry();
            sharedEntries[sharedIndex].setCode(conversionService.convert(serverPetJournalEntry.getJournalEntry(), ru.urvanov.virtualpets.shared.domain.JournalEntryType.class));
            sharedEntries[sharedIndex].setCreatedAt(serverPetJournalEntry.getCreatedAt());
        }
        
        result.setEntries(sharedEntries);
        
        
        return result;
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

        PetInfo[] petInfos;
        if (pets != null) {
            int length = pets.toArray().length;
            petInfos = new PetInfo[length];
            int n = 0;
            for (Pet p : pets) {
                petInfos[n] = new PetInfo();
                petInfos[n].setName(p.getName());
                petInfos[n].setId(p.getId());
                petInfos[n]
                        .setPetType(conversionService.convert(
                                p.getPetType(),
                                ru.urvanov.virtualpets.shared.domain.PetType.class));
                n++;
            }
        } else {
            petInfos = new PetInfo[0];
        }
        PetListResult result = new PetListResult();
        result.setSuccess(true);
        result.setPetsInfo(petInfos);
        return result;
    }

    @Override
    @Transactional(rollbackFor = {ServiceException.class, DaoException.class})
    public CreatePetResult create(CreatePetArg arg) throws DaoException,
            ServiceException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User user = (User) authentication.getPrincipal();
        Pet pet = new Pet();
        pet.setName(arg.getName());
        pet.setCreatedDate(OffsetDateTime.now(clock));
        pet.setUser(userDao.getReference(user.getId()));
        pet.setComment(arg.getComment());
        pet.setPetType(conversionService.convert(arg.getPetType(),
                ru.urvanov.virtualpets.server.dao.domain.PetType.class));
        Level level = levelDao.findById(1);
        pet.setLevel(level);
        petDao.save(pet);
        CreatePetResult result = new CreatePetResult();
        result.setSuccess(true);
        return result;
    }

    @Override
    @Transactional(rollbackFor = {ServiceException.class, DaoException.class})
    public SelectPetResult select(SelectPetArg arg) throws DaoException,
            ServiceException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User user = (User) authentication.getPrincipal();
        Integer userId = user.getId();

        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();

        int id = arg.getPetId();
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
        if (pet.getUser().getId().equals(userId)) {
            sra.setAttribute("pet", new SelectedPet(pet), ServletRequestAttributes.SCOPE_SESSION);
            SelectPetResult result = new SelectPetResult();
            result.setSuccess(true);
            return result;
        } else {
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
        Pet pet = petDao.findFullById(selectedPet.getId());
        DrinkId drinkType =  conversionService.convert(drinkArg.getDrinkType(), DrinkId.class);
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

        FoodId foodType = conversionService.convert(satietyArg.getFoodType(),
                ru.urvanov.virtualpets.server.dao.domain.FoodId.class);
        Pet pet = petDao.findById(selectedPet.getId());
        PetFood food = petFoodDao.findByPetIdAndFoodType(pet.getId(), foodType);
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
        Pet pet = petDao.findById(selectedPet.getId());
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
        
        Map<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer> buildingMaterialCounts = buildingMaterials.entrySet().stream()
                .<Entry<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer>>map(e -> Map.entry(
                        conversionService.convert(e.getKey(), ru.urvanov.virtualpets.shared.domain.BuildingMaterialType.class),
                        e.getValue().getBuildingMaterialCount()))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        
        ru.urvanov.virtualpets.shared.domain.GetPetRucksackInnerResult result = new ru.urvanov.virtualpets.shared.domain.GetPetRucksackInnerResult();
        result.setBuildingMaterialCounts(buildingMaterialCounts);
        return result;
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

}
