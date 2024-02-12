/**
 * 
 */
package ru.urvanov.virtualpets.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ru.urvanov.virtualpets.server.dao.AchievementDao;
import ru.urvanov.virtualpets.server.dao.ClothDao;
import ru.urvanov.virtualpets.server.dao.DrinkDao;
import ru.urvanov.virtualpets.server.dao.JournalEntryDao;
import ru.urvanov.virtualpets.server.dao.LevelDao;
import ru.urvanov.virtualpets.server.dao.PetDao;
import ru.urvanov.virtualpets.server.dao.PetFoodDao;
import ru.urvanov.virtualpets.server.dao.PetJournalEntryDao;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.Achievement;
import ru.urvanov.virtualpets.server.dao.domain.AchievementCode;
import ru.urvanov.virtualpets.server.dao.domain.Book;
import ru.urvanov.virtualpets.server.dao.domain.Bookcase;
import ru.urvanov.virtualpets.server.dao.domain.BookcaseCost;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.Cloth;
import ru.urvanov.virtualpets.server.dao.domain.Drink;
import ru.urvanov.virtualpets.server.dao.domain.DrinkType;
import ru.urvanov.virtualpets.server.dao.domain.FoodType;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntry;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryType;
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
/**
 * @author fedya
 *
 */
@Service("petService")
public class PetServiceImpl implements PetService, ru.urvanov.virtualpets.shared.service.PetService {

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
    private AchievementDao achievementDao;
    
    @Autowired
    private JournalEntryDao journalEntryDao;
    
    @Autowired
    private DrinkDao drinkDao;
    
    @Autowired
    private ConversionService conversionService;

    
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

    /**
     * @return the petDao
     */
    public PetDao getPetDao() {
        return petDao;
    }

    /**
     * @param petDao the petDao to set
     */
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
        Map<BuildingMaterial, PetBuildingMaterial> petBuildingMaterials = fullPet.getBuildingMaterials();
        Map<BuildingMaterial, RefrigeratorCost> resourceCosts =  refrigerator.getRefrigeratorCost();
        for (Entry<BuildingMaterial, RefrigeratorCost> entry : resourceCosts.entrySet()) {
            BuildingMaterial buildingMaterial = entry.getKey();
            RefrigeratorCost resourceCost = entry.getValue();
            PetBuildingMaterial petBuildingMaterial = petBuildingMaterials.get(buildingMaterial);
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
        Map<BuildingMaterial, PetBuildingMaterial> petBuildingMaterials = fullPet.getBuildingMaterials();
        Map<BuildingMaterial, BookcaseCost> resourceCosts =  bookcase.getBookcaseCost();
        for (Entry<BuildingMaterial, BookcaseCost> entry : resourceCosts.entrySet()) {
            BuildingMaterial buildingMaterial = entry.getKey();
            BookcaseCost resourceCost = entry.getValue();
            PetBuildingMaterial petBuildingMaterial = petBuildingMaterials.get(buildingMaterial);
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
        Map<BuildingMaterial, PetBuildingMaterial> petBuildingMaterials = fullPet.getBuildingMaterials();
        Map<BuildingMaterial, MachineWithDrinksCost> resourceCosts =  drink.getMachineWithDrinksCost();
        for (Entry<BuildingMaterial, MachineWithDrinksCost> entry : resourceCosts.entrySet()) {
            BuildingMaterial buildingMaterial = entry.getKey();
            MachineWithDrinksCost resourceCost = entry.getValue();
            PetBuildingMaterial petBuildingMaterial = petBuildingMaterials.get(buildingMaterial);
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
    public List<AchievementCode> calculateAchievements(Pet fullPet) {
        List<AchievementCode> result = new ArrayList<AchievementCode>();
        Map<Achievement, PetAchievement> map = fullPet.getAchievements();
        for (PetAchievement pa : map.values()) {
            if (!pa.getWasShown()) {
                pa.setWasShown(true);
                result.add(pa.getAchievement().getCode());
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
        Pet pet = petDao.findFullById(selectedPet.getId());
        Set<Book> books = pet.getBooks();
        
        boolean[] resultBooks = new boolean[Book.MAX_ID];
        books.stream().forEach((b)->{resultBooks[b.getId() - 1] = true;});
        GetPetBooksResult result = new GetPetBooksResult();
        result.setBooks(resultBooks);
        return result;
    }
    

    @Override
    public GetPetClothsResult getPetCloths() {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet", ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findFullById(selectedPet.getId());
        Set<Cloth> cloths = pet.getCloths();
        ru.urvanov.virtualpets.shared.domain.Cloth[] sharedCloths = new ru.urvanov.virtualpets.shared.domain.Cloth[cloths.size()];
        
        int n = 0;
        for (Cloth cloth : cloths) {
            ru.urvanov.virtualpets.shared.domain.Cloth sharedCloth = new ru.urvanov.virtualpets.shared.domain.Cloth();
            sharedCloth.setId(cloth.getId());
            sharedCloth.setClothType(conversionService.convert(cloth.getClothType(), ru.urvanov.virtualpets.shared.domain.ClothType.class));
            sharedCloths[n] = sharedCloth;
            n++;
        }

        ru.urvanov.virtualpets.shared.domain.GetPetClothsResult result = new ru.urvanov.virtualpets.shared.domain.GetPetClothsResult();
        result.setCloths(sharedCloths);
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
    public void savePetCloths(SavePetCloths saveClothArg) {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet", ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findById(selectedPet.getId());
        Cloth hat = null;
        if (saveClothArg.getHatId() != null) {
            hat = clothDao.getReference(saveClothArg.getHatId());
        }
        Cloth cloth = null;
        if (saveClothArg.getClothId() != null) {
            cloth = clothDao.getReference(saveClothArg.getClothId());
        }
        Cloth bow = null;
        if (saveClothArg.getBowId() != null) {
            bow = clothDao.getReference(saveClothArg.getBowId());
        }
        pet.setHat(hat);
        pet.setCloth(cloth);
        pet.setBow(bow);
        petDao.save(pet);
    }
    
    @Override
    public GetPetDrinksResult getPetDrinks() {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet", ServletRequestAttributes.SCOPE_SESSION);
        Pet fullPet = petDao.findFullById(selectedPet.getId());
        Map<Drink, PetDrink> drinks = fullPet.getDrinks();
        Map<ru.urvanov.virtualpets.shared.domain.DrinkType, Integer> drinkCounts = new HashMap<ru.urvanov.virtualpets.shared.domain.DrinkType, Integer>();
        for (PetDrink petDrink : drinks.values()) {
            ru.urvanov.virtualpets.shared.domain.DrinkType sharedDrinkType = conversionService.convert(petDrink.getDrink().getDrinkType(), ru.urvanov.virtualpets.shared.domain.DrinkType.class);
            drinkCounts.put(sharedDrinkType, petDrink.getDrinkCount());
        }
        ru.urvanov.virtualpets.shared.domain.GetPetDrinksResult result = new ru.urvanov.virtualpets.shared.domain.GetPetDrinksResult();
        result.setDrinkCounts(drinkCounts);
        return result;
    }

    @Override
    public GetPetFoodsResult getPetFoods() {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet", ServletRequestAttributes.SCOPE_SESSION);
        List<PetFood> petFoods = petFoodDao.findByPetId(selectedPet.getId());
        Map<ru.urvanov.virtualpets.shared.domain.FoodType, Integer> foodCounts = new HashMap<ru.urvanov.virtualpets.shared.domain.FoodType, Integer>();
        for (PetFood petFood : petFoods) {
            ru.urvanov.virtualpets.shared.domain.FoodType sharedFoodType = conversionService.convert(petFood.getFood().getCode(), ru.urvanov.virtualpets.shared.domain.FoodType.class);
            foodCounts.put(sharedFoodType, petFood.getFoodCount());
        }
        ru.urvanov.virtualpets.shared.domain.GetPetFoodsResult result = new ru.urvanov.virtualpets.shared.domain.GetPetFoodsResult();
        result.setFoodCounts(foodCounts);
        return result;
    }

    @Override
    public GetPetJournalEntriesResult getPetJournalEntries(int count) {
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
            sharedEntries[sharedIndex].setCode(conversionService.convert(serverPetJournalEntry.getJournalEntry().getCode(), ru.urvanov.virtualpets.shared.domain.JournalEntryType.class));
            sharedEntries[sharedIndex].setCreatedAt(serverPetJournalEntry.getCreatedAt());
        }
        
        result.setEntries(sharedEntries);
        
        
        return result;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    


    /*
     * (non-Javadoc)
     * 
     * @see
     * ru.urvanov.virtualpets.shared.service.PetService#getUserPets(ru.
     * urvanov.virtualpets.shared.domain.PetListArg)
     */
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * ru.urvanov.virtualpets.shared.service.PetService#create(ru.urvanov
     * .virtualpets.shared.domain.CreatePetArg)
     */
    @Override
    @Transactional
    public CreatePetResult create(CreatePetArg arg) throws DaoException,
            ServiceException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User user = (User) authentication.getPrincipal();
        Pet pet = new Pet();
        pet.setName(arg.getName());
        pet.setCreatedDate(new Date());
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * ru.urvanov.virtualpets.shared.service.PetService#select(ru.urvanov
     * .virtualpets.shared.domain.SelectPetArg)
     */
    @Override
    public SelectPetResult select(SelectPetArg arg) throws DaoException,
            ServiceException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User user = (User) authentication.getPrincipal();
        Integer userId = user.getId();

        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();

        int id = arg.getPetId();
        Pet pet = petDao.findById(id);
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
    @Transactional
    public void drink(DrinkArg drinkArg) throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findFullById(selectedPet.getId());
        DrinkType drinkType =  conversionService.convert(drinkArg.getDrinkType(), DrinkType.class);
        Map<Drink, PetDrink> drinks = pet.getDrinks();
        PetDrink petDrink = drinks.get(drinkDao.findByCode(drinkType));
        petDrink.setDrinkCount(petDrink.getDrinkCount() - 1);
        pet.setDrink(100);
        JournalEntry buildRefrigeratorJournalEntry = journalEntryDao.findByCode(JournalEntryType.BUILD_REFRIGERATOR);
        if (pet.getJournalEntries().get(buildRefrigeratorJournalEntry) == null) {
            PetJournalEntry newPetJournalEntry = new PetJournalEntry();
            newPetJournalEntry.setCreatedAt(new Date());
            newPetJournalEntry.setPet(pet);
            newPetJournalEntry.setJournalEntry(buildRefrigeratorJournalEntry);
            newPetJournalEntry.setReaded(false);
            pet.getJournalEntries().put(newPetJournalEntry.getJournalEntry(), newPetJournalEntry);
        }
        sra.setAttribute("pet", new SelectedPet(pet), ServletRequestAttributes.SCOPE_SESSION);
        if (pet.getDrinkCount() < Integer.MAX_VALUE) pet.setDrinkCount(pet.getDrinkCount() + 1);
        Achievement achievementDrink1 = achievementDao.findByCode(AchievementCode.DRINK_1);
        Achievement achievementDrink10 = achievementDao.findByCode(AchievementCode.DRINK_10);
        Achievement achievementDrink100 = achievementDao.findByCode(AchievementCode.DRINK_100);
        if (pet.getDrinkCount().equals(Integer.valueOf(1))) addAchievementIfNot(pet, achievementDrink1);
        if (pet.getDrinkCount().equals(Integer.valueOf(10))) addAchievementIfNot(pet, achievementDrink10);
        if (pet.getDrinkCount().equals(Integer.valueOf(100))) addAchievementIfNot(pet, achievementDrink100);
        addExperience(petDao.findById(pet.getId()), 1);
        petDao.save(pet);
        
        
    }

    public void addAchievementIfNot(Pet pet, Achievement achievement) {
        if (!pet.getAchievements().containsKey(achievement)) {
            PetAchievement petAchievement = new PetAchievement();
            petAchievement.setPet(pet);
            petAchievement.setAchievement(achievement);
            pet.getAchievements().put(achievement, petAchievement);
        }
    }

    @Override
    @Transactional
    public void satiety(SatietyArg satietyArg) throws DaoException,
            ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);

        FoodType foodType = conversionService.convert(satietyArg.getFoodType(),
                ru.urvanov.virtualpets.server.dao.domain.FoodType.class);
        Pet pet = petDao.findById(selectedPet.getId());
        PetFood food = petFoodDao.findByPetIdAndFoodType(pet.getId(), foodType);
        if (food == null) {
            throw new ServiceException("Food count = 0.");
        } else {
            if (food.getFoodCount().equals(0)) {
                throw new ServiceException("Food count = 0.");
            }
            food.setFoodCount(food.getFoodCount() - 1);
            petFoodDao.save(food);
        }
        pet.setSatiety(100);
        JournalEntry buildBookcaseJournalEntry = journalEntryDao.findByCode(JournalEntryType.BUILD_BOOKCASE);
        if (pet.getJournalEntries().get(buildBookcaseJournalEntry) == null) {
            PetJournalEntry newPetJournalEntry = new PetJournalEntry();
            newPetJournalEntry.setCreatedAt(new Date());
            newPetJournalEntry.setPet(pet);
            newPetJournalEntry.setJournalEntry(buildBookcaseJournalEntry);
            newPetJournalEntry.setReaded(false);
            pet.getJournalEntries().put(newPetJournalEntry.getJournalEntry(), newPetJournalEntry);
        }
        sra.setAttribute("pet", new SelectedPet(pet), ServletRequestAttributes.SCOPE_SESSION);
        if (pet.getEatCount() < Integer.MAX_VALUE) pet.setEatCount(pet.getEatCount() + 1);
        Achievement achievementEat1 = achievementDao.findByCode(AchievementCode.FEED_1);
        Achievement achievementEat10 = achievementDao.findByCode(AchievementCode.FEED_10);
        Achievement achievementEat100 = achievementDao.findByCode(AchievementCode.FEED_100);
        if (pet.getEatCount().equals(Integer.valueOf(1))) addAchievementIfNot(pet, achievementEat1);
        if (pet.getEatCount().equals(Integer.valueOf(10))) addAchievementIfNot(pet, achievementEat10);
        if (pet.getEatCount().equals(Integer.valueOf(100))) addAchievementIfNot(pet, achievementEat100);
        addExperience(petDao.findById(pet.getId()), 1);
        petDao.save(pet);
        
    }

    @Override
    @Transactional
    public void education() throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet)  sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findById(selectedPet.getId());
        pet.setEducation(100);
        
        JournalEntry leaveRoomJournalEntry = journalEntryDao.findByCode(JournalEntryType.LEAVE_ROOM);
        if (pet.getJournalEntries().get(leaveRoomJournalEntry) == null) {
            PetJournalEntry newPetJournalEntry = new PetJournalEntry();
            newPetJournalEntry.setCreatedAt(new Date());
            newPetJournalEntry.setPet(pet);
            newPetJournalEntry.setJournalEntry(leaveRoomJournalEntry);
            newPetJournalEntry.setReaded(false);
            pet.getJournalEntries().put(newPetJournalEntry.getJournalEntry(), newPetJournalEntry);
        }
        
        sra.setAttribute("pet", new SelectedPet(pet), ServletRequestAttributes.SCOPE_SESSION);
        if (pet.getTeachCount() < Integer.MAX_VALUE) pet.setTeachCount(pet.getTeachCount() + 1);
        Achievement achievementTeach1 = achievementDao.findByCode(AchievementCode.TEACH_1);
        Achievement achievementTeach10 = achievementDao.findByCode(AchievementCode.TEACH_10);
        Achievement achievementTeach100 = achievementDao.findByCode(AchievementCode.TEACH_100);
        if (pet.getTeachCount().equals(Integer.valueOf(1))) addAchievementIfNot(pet, achievementTeach1);
        if (pet.getTeachCount().equals(Integer.valueOf(10))) addAchievementIfNot(pet, achievementTeach10);
        if (pet.getTeachCount().equals(Integer.valueOf(100))) addAchievementIfNot(pet, achievementTeach100);
        addExperience(petDao.findById(pet.getId()), 1);
        petDao.save(pet);
        
    }

    @Override
    public void mood() throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        Pet pet = (Pet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        pet = petDao.findById(pet.getId());
        pet.setMood(100);
        sra.setAttribute("pet", new SelectedPet(pet), ServletRequestAttributes.SCOPE_SESSION);
        addExperience(petDao.findById(pet.getId()), 1);
        petDao.save(pet);
        
    }
    
    @Override
    public GetPetRucksackInnerResult getPetRucksackInner()
            throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet", ServletRequestAttributes.SCOPE_SESSION);
        Pet fullPet = petDao.findFullById(selectedPet.getId());
        Map<BuildingMaterial, PetBuildingMaterial> buildingMaterials = fullPet.getBuildingMaterials();
        Map<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer> buildMaterialCounts = new HashMap<ru.urvanov.virtualpets.shared.domain.BuildingMaterialType, Integer>();
        for (PetBuildingMaterial bm : buildingMaterials.values()) {
            ru.urvanov.virtualpets.shared.domain.BuildingMaterialType sharedBuildMaterialType = conversionService.convert(bm.getBuildingMaterial().getCode(), ru.urvanov.virtualpets.shared.domain.BuildingMaterialType.class);
            buildMaterialCounts.put(sharedBuildMaterialType, bm.getBuildingMaterialCount());
        }
        ru.urvanov.virtualpets.shared.domain.GetPetRucksackInnerResult result = new ru.urvanov.virtualpets.shared.domain.GetPetRucksackInnerResult();
        result.setBuildingMaterialCounts(buildMaterialCounts);
        return result;
    }

    @Override
    public PetDetails petInformationPage(Integer id) {
        Pet fullPet = petDao.findFullById(id);
        List<Achievement> allAchievements = achievementDao.findAll();
        PetDetails result = new PetDetails();
        result.setId(fullPet.getId());
        result.setName(fullPet.getName());
        result.setLevel(fullPet.getLevel().getId());
        List<PetInformationPageAchievement> achievements = new ArrayList<>();
        result.setAchievements(achievements);
        for (Achievement achievement : allAchievements) {
            PetInformationPageAchievement petInformationPageAchievement = new PetInformationPageAchievement();
            petInformationPageAchievement.setCode(achievement.getCode().name());
            petInformationPageAchievement.setUnlocked(fullPet.getAchievements().containsKey(achievement));
            achievements.add(petInformationPageAchievement);
        }
        return result;
    }

}
