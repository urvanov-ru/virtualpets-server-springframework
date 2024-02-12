/**
 * 
 */
package ru.urvanov.virtualpets.server.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ru.urvanov.virtualpets.server.dao.AchievementDao;
import ru.urvanov.virtualpets.server.dao.BookDao;
import ru.urvanov.virtualpets.server.dao.BuildingMaterialDao;
import ru.urvanov.virtualpets.server.dao.ClothDao;
import ru.urvanov.virtualpets.server.dao.DrinkDao;
import ru.urvanov.virtualpets.server.dao.FoodDao;
import ru.urvanov.virtualpets.server.dao.LevelDao;
import ru.urvanov.virtualpets.server.dao.PetDao;
import ru.urvanov.virtualpets.server.dao.PetFoodDao;
import ru.urvanov.virtualpets.server.dao.RoomDao;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.Achievement;
import ru.urvanov.virtualpets.server.dao.domain.AchievementCode;
import ru.urvanov.virtualpets.server.dao.domain.Book;
import ru.urvanov.virtualpets.server.dao.domain.Bookcase;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialType;
import ru.urvanov.virtualpets.server.dao.domain.Cloth;
import ru.urvanov.virtualpets.server.dao.domain.Drink;
import ru.urvanov.virtualpets.server.dao.domain.DrinkType;
import ru.urvanov.virtualpets.server.dao.domain.FoodType;
import ru.urvanov.virtualpets.server.dao.domain.HiddenObjectsCollected;
import ru.urvanov.virtualpets.server.dao.domain.HiddenObjectsGame;
import ru.urvanov.virtualpets.server.dao.domain.HiddenObjectsPlayer;
import ru.urvanov.virtualpets.server.dao.domain.HiddenObjectsReward;
import ru.urvanov.virtualpets.server.dao.domain.Level;
import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetBuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.PetDrink;
import ru.urvanov.virtualpets.server.dao.domain.PetFood;
import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;
import ru.urvanov.virtualpets.server.dao.domain.Room;
import ru.urvanov.virtualpets.server.dao.domain.SelectedPet;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.shared.domain.HiddenObjectsGameType;
import ru.urvanov.virtualpets.shared.domain.LevelInfo;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;
import ru.urvanov.virtualpets.shared.service.HiddenObjectsService;

/**
 * @author fedya
 * 
 */
@Service
public class HiddenObjectsServiceImpl implements HiddenObjectsService {
    private static final String HIDDEN_OBJECTS_GAME_ID = "hiddenObjectsGameId";

    private static final String HIDDEN_OBJECTS_GAME_STARTED = "hiddenObjectsGameStarted";
    private static final String HIDDEN_OBJECTS_GAME_OVER = "hiddenObjectsGameOver";
    private static final String HIDDEN_OBJECTS_GAME_TYPE = "hiddenObjectsGameType";
    private static final int MAX_OBJECTS_FOR_SEARCH = 8;
    private static final int GAME_TIMEOUT_SECONDS = 30;

    private static final int TREASURY_HIDDEN_OBJECTS_COUNT = 42;
    private static final int RUBBISH_HIDDEN_OBJECTS_COUNT = 52;
    private static final int AFTERNOONTEA_HIDDEN_OBJECTS_COUNT = 56;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private PetFoodDao petFoodDao;

    @Autowired
    private FoodDao foodDao;

    @Autowired
    private PetDao petDao;
    
    @Autowired
    private PetService petService;

    @Autowired
    private DrinkDao drinkDao;

    @Autowired
    private ClothDao clothDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private LevelDao levelDao;

    @Autowired
    private BuildingMaterialDao buildingMaterialDao;

    @Autowired
    private AchievementDao achievementDao;

    private Map<Integer, HiddenObjectsGame> games = new HashMap<Integer, HiddenObjectsGame>();
    private Map<Integer, HiddenObjectsGame> finishedGames = new HashMap<Integer, HiddenObjectsGame>();
    private Map<HiddenObjectsGameType, Map<Integer, HiddenObjectsGame>> notStartedGames = new HashMap<HiddenObjectsGameType, Map<Integer, HiddenObjectsGame>>();
    private int lastGameId;

    @Override
    public synchronized ru.urvanov.virtualpets.shared.domain.HiddenObjectsGame joinGame(
            ru.urvanov.virtualpets.shared.domain.JoinHiddenObjectsGameArg arg)
            throws DaoException, ServiceException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = (Authentication) securityContext
                .getAuthentication();
        User user = (User) authentication.getPrincipal();
        user = userDao.findById(user.getId());
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        
        Pet pet = petDao.findById(selectedPet.getId());

        HiddenObjectsGame foundGame = null;
        HiddenObjectsPlayer player = new HiddenObjectsPlayer();
        player.setUserId(user.getId());
        player.setPetId(pet.getId());
        player.setUserName(user.getName());
        player.setPetName(pet.getName());
        Cloth hat = pet.getHat();
        Cloth cloth = pet.getCloth();
        Cloth bow = pet.getBow();
        if (hat != null) {
            player.setHatId(hat.getId());
        }
        if (cloth != null) {
            player.setClothId(cloth.getId());
        }
        if (bow != null) {
            player.setBowId(bow.getId());
        }
        Map<Integer, HiddenObjectsGame> map = notStartedGames.get(arg
                .getHiddenObjectsGameType());
        if (map == null) {
            map = new HashMap<Integer, HiddenObjectsGame>();
            notStartedGames.put(arg.getHiddenObjectsGameType(), map);
        }
        for (HiddenObjectsGame hig : map.values()) {
            if (hig.getPetsCount() < HiddenObjectsGame.MAX_PLAYERS_COUNT) {
                hig.addPlayer(player);
                foundGame = hig;
                break;
            }
        }
        if (foundGame == null) {
            foundGame = new HiddenObjectsGame();
            foundGame.addPlayer(player);
            lastGameId++;
            map.put(lastGameId, foundGame);
        }

        sra.setAttribute(HIDDEN_OBJECTS_GAME_ID, lastGameId,
                ServletRequestAttributes.SCOPE_SESSION);
        sra.setAttribute(HIDDEN_OBJECTS_GAME_STARTED, false,
                ServletRequestAttributes.SCOPE_SESSION);
        sra.setAttribute(HIDDEN_OBJECTS_GAME_OVER, false,
                ServletRequestAttributes.SCOPE_SESSION);
        sra.setAttribute(HIDDEN_OBJECTS_GAME_TYPE,
                arg.getHiddenObjectsGameType(),
                ServletRequestAttributes.SCOPE_SESSION);
        return getResult(foundGame);
    }

    private ru.urvanov.virtualpets.shared.domain.HiddenObjectsGame getResult(
            HiddenObjectsGame foundGame) {
        ru.urvanov.virtualpets.shared.domain.HiddenObjectsGame result = new ru.urvanov.virtualpets.shared.domain.HiddenObjectsGame();
        result.setObjects(foundGame.getObjectsForSearch());
        HiddenObjectsPlayer[] players = foundGame.getDisplayablePlayers();
        for (int n = 0; n < players.length; n++) {
            if (players[n] != null) {
                ru.urvanov.virtualpets.shared.domain.HiddenObjectsPlayer resultPlayer = new ru.urvanov.virtualpets.shared.domain.HiddenObjectsPlayer();
                resultPlayer.setPetId(players[n].getPetId());
                resultPlayer.setPetName(players[n].getPetName());
                resultPlayer.setUserId(players[n].getUserId());
                resultPlayer.setUserName(players[n].getUserName());
                resultPlayer.setHatId(players[n].getHatId());
                resultPlayer.setClothId(players[n].getClothId());
                resultPlayer.setBowId(players[n].getBowId());
                result.addPlayer(resultPlayer);
            }
        }
        Integer[] objects = new Integer[4];
        Integer[] objectsForSearch = foundGame.getObjectsForSearch();
        int n = 0;
        while ((n < HiddenObjectsGame.COUNT_DISPLAYABLE_OBJECTS)
                && (n < objectsForSearch.length)) {
            objects[n] = objectsForSearch[n];
            n++;
        }
        result.setObjects(objects);

        HiddenObjectsCollected[] collectedObjects = foundGame
                .getCollectedObjects();
        ru.urvanov.virtualpets.shared.domain.HiddenObjectsCollected[] resultCollectedObjects = new ru.urvanov.virtualpets.shared.domain.HiddenObjectsCollected[collectedObjects.length];
        for (n = 0; n < collectedObjects.length; n++) {
            ru.urvanov.virtualpets.shared.domain.HiddenObjectsCollected hoc = new ru.urvanov.virtualpets.shared.domain.HiddenObjectsCollected();
            hoc.setObjectId(collectedObjects[n].getObjectId());
            hoc.setPlayer(result.getPlayer(collectedObjects[n].getPlayer()
                    .getUserId()));
            resultCollectedObjects[n] = hoc;
        }
        result.setCollectedObjects(resultCollectedObjects);
        result.setGameStarted(foundGame.isStarted());
        result.setGameOver(foundGame.isGameOver());
        if (foundGame.isGameOver()) {
            ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                    ServletRequestAttributes.SCOPE_SESSION);
            Pet pet = petDao.findById(selectedPet.getId());
            HiddenObjectsPlayer player = foundGame.getPlayer(pet.getUser()
                    .getId());
            ru.urvanov.virtualpets.shared.domain.HiddenObjectsReward resultReward = new ru.urvanov.virtualpets.shared.domain.HiddenObjectsReward();
            HiddenObjectsReward playerReward = player.getReward();
            resultReward.setFood(conversionService.convert(
                    playerReward.getFood(),
                    ru.urvanov.virtualpets.shared.domain.FoodType.class));
            resultReward.setClothId(playerReward.getClothId());
            resultReward.setBookId(playerReward.getBookId());
            resultReward.setDrinkType(conversionService.convert(
                    playerReward.getDrinkType(),
                    ru.urvanov.virtualpets.shared.domain.DrinkType.class));
            resultReward.setLevelInfo(playerReward.getLevelInfo());
            resultReward.setExperience(playerReward.getExperience());
            resultReward
                    .setBuildingMaterialType(conversionService.convert(
                            playerReward.getBuildingMaterialType(),
                            ru.urvanov.virtualpets.shared.domain.BuildingMaterialType.class));
            ru.urvanov.virtualpets.server.dao.domain.AchievementCode[] achievements = playerReward
                    .getAchievements();
            ru.urvanov.virtualpets.shared.domain.AchievementCode[] sharedAchievements = new ru.urvanov.virtualpets.shared.domain.AchievementCode[achievements.length];
            for (int m = 0; m < achievements.length; m++) {
                sharedAchievements[m] = conversionService
                        .convert(
                                achievements[m],
                                ru.urvanov.virtualpets.shared.domain.AchievementCode.class);
            }
            resultReward.setAchievements(sharedAchievements);
            result.setReward(resultReward);
        }
        if (foundGame.getStartTime() != null) {
            Calendar time = Calendar.getInstance();
            Long secondsLeft = GAME_TIMEOUT_SECONDS
                    - (time.getTimeInMillis() - foundGame.getStartTime()
                            .getTimeInMillis()) / 1000;
            result.setSecondsLeft(secondsLeft.intValue());
        }

        return result;
    }

    @Override
    public synchronized ru.urvanov.virtualpets.shared.domain.HiddenObjectsGame getGameInfo()
            throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        Integer gameId = (Integer) sra.getAttribute(HIDDEN_OBJECTS_GAME_ID,
                ServletRequestAttributes.SCOPE_SESSION);
        Boolean hiddenObjectsGameStarted = (Boolean) sra.getAttribute(
                HIDDEN_OBJECTS_GAME_STARTED,
                ServletRequestAttributes.SCOPE_SESSION);
        Boolean hiddenObjectsGameOver = (Boolean) sra.getAttribute(
                HIDDEN_OBJECTS_GAME_OVER,
                ServletRequestAttributes.SCOPE_SESSION);
        HiddenObjectsGameType hiddenObjectsGameType = (HiddenObjectsGameType) sra
                .getAttribute(HIDDEN_OBJECTS_GAME_TYPE,
                        ServletRequestAttributes.SCOPE_SESSION);
        if (hiddenObjectsGameStarted == null) {
            hiddenObjectsGameStarted = false;
        }
        if (hiddenObjectsGameOver == null) {
            hiddenObjectsGameOver = null;
        }
        if (gameId == null) {
            throw new ServiceException("No game with such id");
        }
        HiddenObjectsGame foundGame = null;
        Map<Integer, HiddenObjectsGame> map = notStartedGames
                .get(hiddenObjectsGameType);
        if (!hiddenObjectsGameStarted && map != null) {
            foundGame = map.get(gameId);
        }
        if ((foundGame == null) && (!hiddenObjectsGameOver)) {
            foundGame = games.get(gameId);
            if (foundGame != null) {
                Calendar startTime = foundGame.getStartTime();
                Calendar time = Calendar.getInstance();
                time.add(Calendar.SECOND, -GAME_TIMEOUT_SECONDS);
                if (time.after(startTime)) {
                    finishGame(foundGame, gameId);
                }
            }
        }
        if (foundGame == null) {
            foundGame = finishedGames.get(gameId);
            if ((gameId != null) && (!hiddenObjectsGameOver)) {
                hiddenObjectsGameOver = true;
                sra.setAttribute(HIDDEN_OBJECTS_GAME_OVER, true,
                        ServletRequestAttributes.SCOPE_SESSION);
            }
        } else {
            if (foundGame.isStarted() != hiddenObjectsGameStarted) {
                sra.setAttribute(HIDDEN_OBJECTS_GAME_STARTED,
                        foundGame.isStarted(),
                        ServletRequestAttributes.SCOPE_SESSION);
            }
            if (foundGame.isGameOver() != hiddenObjectsGameOver) {
                sra.setAttribute(HIDDEN_OBJECTS_GAME_OVER,
                        foundGame.isGameOver(),
                        ServletRequestAttributes.SCOPE_SESSION);
            }
        }
        return getResult(foundGame);
    }

    @Override
    public synchronized ru.urvanov.virtualpets.shared.domain.HiddenObjectsGame collectObject(
            ru.urvanov.virtualpets.shared.domain.CollectObjectArg arg)
            throws DaoException, ServiceException {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = (Authentication) securityContext
                .getAuthentication();
        User user = (User) authentication.getPrincipal();
        Integer userId = user.getId();

        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        Integer gameId = (Integer) sra.getAttribute(HIDDEN_OBJECTS_GAME_ID,
                ServletRequestAttributes.SCOPE_SESSION);
        Boolean hiddenObjectsGameStarted = (Boolean) sra.getAttribute(
                HIDDEN_OBJECTS_GAME_STARTED,
                ServletRequestAttributes.SCOPE_SESSION);
        if (hiddenObjectsGameStarted == null) {
            hiddenObjectsGameStarted = false;
        }
        if (gameId == null) {
            throw new ServiceException("No game with such id");
        }
        HiddenObjectsGame foundGame = null;
        if (!hiddenObjectsGameStarted) {
            throw new ServiceException("Game not started.");
        }
        if (foundGame == null) {
            foundGame = games.get(gameId);
        }
        if (foundGame != null) {
            Integer collectObjectId = arg.getObjectId();
            Integer[] objectsForSearch = foundGame.getObjectsForSearch();
            for (int n = 0; n < HiddenObjectsGame.COUNT_DISPLAYABLE_OBJECTS; n++) {
                Integer objectId = objectsForSearch[n];
                if (objectId != null) {
                    if (objectId.equals(collectObjectId)) {
                        foundGame.removeObjectForSearch(objectId);
                        HiddenObjectsPlayer player = foundGame
                                .getPlayer(userId);
                        player.setScore(player.getScore() + 1);
                        foundGame.addCollectedObject(collectObjectId, player);
                        int objCount = foundGame.getObjectsForSearchCount();
                        if (objCount == 0) {
                            finishGame(foundGame, gameId);
                        }
                    }
                }
            }
        } else {
            foundGame = finishedGames.get(gameId);
        }
        return getResult(foundGame);
    }

    private void finishGame(HiddenObjectsGame foundGame, Integer gameId) {
        foundGame.setGameOver(true);
        games.remove(gameId);
        finishedGames.put(gameId, foundGame);
        calculateReward(foundGame);
    }

    private void calculateReward(HiddenObjectsGame game) {
        HiddenObjectsPlayer[] players = game.getDisplayablePlayers();
        for (HiddenObjectsPlayer player : players) {
            if (player != null) {
                int score = player.getScore();
                FoodType foodType = FoodType.DRY_FOOD;
                Integer clothId = null;
                BuildingMaterialType buildingMaterialType = null;
                Integer bookId = null;
                DrinkType drinkType = null;
                if (score > MAX_OBJECTS_FOR_SEARCH
                        / HiddenObjectsGame.MAX_PLAYERS_COUNT) {
                    if (game.getObjectsForSearchCount() == 0) {
                        Random random = new Random();
                        random.nextInt(MAX_OBJECTS_FOR_SEARCH);
                        Room room = roomDao.findByPetId(player.getPetId());
                        Refrigerator refrigerator = room.getRefrigerator();
                        MachineWithDrinks machineWithDrinks = room
                                .getMachineWithDrinks();
                        Bookcase bookcase = room.getBookcase();
                        if (refrigerator != null) {
                            foodType = FoodType.values()[random
                                    .nextInt(refrigerator.getMaxFoodType() + 1)];
                        }
                        if (random.nextInt(100) < 10) {
                            clothId = random.nextInt(clothDao.getCount()) + 1;
                        }
                        if (random.nextInt(100) < 10) {
                            int buildingMaterialRandom = random.nextInt(100);
                            if (buildingMaterialRandom < 60) {
                                buildingMaterialType = BuildingMaterialType
                                        .values()[random
                                        .nextInt(BuildingMaterialType.STONE
                                                .ordinal() + 1)];
                            } else if (buildingMaterialRandom < 90) {
                                buildingMaterialType = BuildingMaterialType
                                        .values()[random
                                        .nextInt(BuildingMaterialType.IRON
                                                .ordinal() + 1)];
                            } else {
                                buildingMaterialType = BuildingMaterialType
                                        .values()[random
                                        .nextInt(BuildingMaterialType.values().length + 1)];
                            }
                        }
                        if (machineWithDrinks != null) {
                            drinkType = DrinkType.values()[random
                                    .nextInt(machineWithDrinks.getId())];
                        }
                        if (bookcase != null && random.nextInt(100) < 10) {
                            bookId = random.nextInt(3 * bookcase.getId());
                        }
                    }
                }
                HiddenObjectsReward reward = new HiddenObjectsReward();

                reward.setFood(foodType);

                reward.setBuildingMaterialType(buildingMaterialType);
                reward.setDrinkType(drinkType);
                reward.setBookId(bookId);
                player.setReward(reward);

                PetFood food = petFoodDao.findByPetIdAndFoodType(
                        player.getPetId(), foodType);
                if (food == null) {
                    food = new PetFood();
                    food.setPet(petDao.getReference(player.getPetId()));
                    food.setFood(foodDao.findByCode(foodType));
                    food.setFoodCount(0);
                }
                food.setFoodCount(food.getFoodCount() + 1);
                petFoodDao.save(food);
                Pet fullPet = petDao.findFullById(player.getPetId());
                fullPet.setMood(100);
                Set<Cloth> cloths = fullPet.getCloths();
                boolean clothFound = false;
                for (Cloth cloth : cloths) {
                    if (cloth.getId().equals(clothId)) {
                        clothFound = true;
                    }
                }
                if ((!clothFound) && (clothId != null)) {
                    cloths.add(clothDao.findById(clothId));
                    reward.setClothId(clothId);
                }
                if (buildingMaterialType != null) {
                    Map<BuildingMaterial, PetBuildingMaterial> mapBuildingMaterials = fullPet
                            .getBuildingMaterials();
                    BuildingMaterial buildingMaterial = buildingMaterialDao
                            .findByCode(buildingMaterialType);
                    PetBuildingMaterial petBuildingMaterial = mapBuildingMaterials
                            .get(buildingMaterial);
                    if (petBuildingMaterial == null) {
                        petBuildingMaterial = new PetBuildingMaterial();
                        petBuildingMaterial
                                .setBuildingMaterial(buildingMaterial);
                        petBuildingMaterial.setPet(fullPet);
                        petBuildingMaterial.setBuildingMaterialCount(0);
                        fullPet.getBuildingMaterials().put(buildingMaterial,
                                petBuildingMaterial);
                    }
                    petBuildingMaterial
                            .setBuildingMaterialCount(petBuildingMaterial
                                    .getBuildingMaterialCount() + 1);
                }
                if (drinkType != null) {
                    Map<Drink, PetDrink> mapDrinks = fullPet.getDrinks();
                    Drink drink = drinkDao.findByCode(drinkType);
                    PetDrink petDrink = mapDrinks.get(drink);
                    if (petDrink == null) {
                        petDrink = new PetDrink();
                        petDrink.setDrink(drink);
                        petDrink.setPet(fullPet);
                        petDrink.setDrinkCount(0);
                        fullPet.getDrinks().put(drink, petDrink);
                    }
                    petDrink.setDrinkCount(petDrink.getDrinkCount() + 1);
                }
                boolean bookFound = false;
                for (Book book : fullPet.getBooks()) {
                    if (book.getId().equals(bookId)) {
                        bookFound = true;
                        break;
                    }
                }
                if (bookId != null && !bookFound) {
                    Set<Book> books = fullPet.getBooks();
                    books.add(bookDao.findById(bookId));
                    reward.setBookId(bookId);
                }

                if (fullPet.getHiddenObjectsGameCount() < Integer.MAX_VALUE)
                    fullPet.setHiddenObjectsGameCount(fullPet
                            .getHiddenObjectsGameCount() + 1);
                Achievement achievementHiddenObjectsGame1 = achievementDao
                        .findByCode(AchievementCode.HIDDEN_OBJECTS_GAME_1);
                Achievement achievementHiddenObjectsGame10 = achievementDao
                        .findByCode(AchievementCode.HIDDEN_OBJECTS_GAME_10);
                Achievement achievementHiddenObjectsGame100 = achievementDao
                        .findByCode(AchievementCode.HIDDEN_OBJECTS_GAME_100);
                if (fullPet.getHiddenObjectsGameCount().equals(
                        Integer.valueOf(1)))
                    petService.addAchievementIfNot(fullPet,
                            achievementHiddenObjectsGame1);
                if (fullPet.getHiddenObjectsGameCount().equals(
                        Integer.valueOf(10)))
                    petService.addAchievementIfNot(fullPet,
                            achievementHiddenObjectsGame10);
                if (fullPet.getHiddenObjectsGameCount().equals(
                        Integer.valueOf(100)))
                    petService.addAchievementIfNot(fullPet,
                            achievementHiddenObjectsGame100);

                
                int experienceReward = 1;
                petService.addExperience(fullPet, experienceReward);

                LevelInfo levelInfo = new LevelInfo();
                levelInfo.setExperience(fullPet.getExperience());
                levelInfo.setLevel(fullPet.getLevel().getId());
                levelInfo.setMinExperience(fullPet.getLevel().getExperience());
                Level nextLevel = levelDao.findById(fullPet.getLevel()
                        .getId() + 1);
                if (nextLevel != null) {
                    levelInfo.setMaxExperience(nextLevel.getExperience());
                } else {
                    levelInfo.setMaxExperience(Integer.MAX_VALUE);
                }
                reward.setExperience(experienceReward);
                reward.setLevelInfo(levelInfo);

                ru.urvanov.virtualpets.server.dao.domain.AchievementCode[] achievements = petService
                        .calculateAchievements(fullPet)
                        .toArray(
                                new ru.urvanov.virtualpets.server.dao.domain.AchievementCode[0]);

                reward.setAchievements(achievements);
                petDao.save(fullPet);
            }
        }
    }

    @Override
    public synchronized void leaveGame() {

        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();

        Integer gameId = (Integer) sra.getAttribute("hiddenObjectsGameId",
                ServletRequestAttributes.SCOPE_SESSION);

        HiddenObjectsGameType hiddenObjectsGameType = (HiddenObjectsGameType) sra
                .getAttribute(HIDDEN_OBJECTS_GAME_TYPE,
                        ServletRequestAttributes.SCOPE_SESSION);

        notStartedGames.get(hiddenObjectsGameType).remove(gameId);
        games.remove(gameId);
    }

    /**
     * @return the conversionService
     */
    public ConversionService getConversionService() {
        return conversionService;
    }

    /**
     * @param conversionService
     *            the conversionService to set
     */
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public synchronized ru.urvanov.virtualpets.shared.domain.HiddenObjectsGame startGame()
            throws DaoException, ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        Integer gameId = (Integer) sra.getAttribute(HIDDEN_OBJECTS_GAME_ID,
                ServletRequestAttributes.SCOPE_SESSION);
        Boolean hiddenObjectsGameStarted = (Boolean) sra.getAttribute(
                HIDDEN_OBJECTS_GAME_STARTED,
                ServletRequestAttributes.SCOPE_SESSION);
        HiddenObjectsGameType hiddenObjectsGameType = (HiddenObjectsGameType) sra
                .getAttribute(HIDDEN_OBJECTS_GAME_TYPE,
                        ServletRequestAttributes.SCOPE_SESSION);
        if (hiddenObjectsGameStarted == null) {
            hiddenObjectsGameStarted = false;
        }
        HiddenObjectsGame game = null;
        Map<Integer, HiddenObjectsGame> map = notStartedGames
                .get(hiddenObjectsGameType);
        if (!hiddenObjectsGameStarted && map != null) {
            game = map.get(gameId);
        }
        if (game == null) {
            game = games.get(gameId);
            if (game != null) {
                return getResult(game);
            }
        }
        if (game == null) {
            throw new ServiceException("No game found with such id.");
        }

        Integer[] objectsForSearch = new Integer[MAX_OBJECTS_FOR_SEARCH];

        int hiddenObjectsCount;
        switch (hiddenObjectsGameType) {
        case TREASURY:
            hiddenObjectsCount = TREASURY_HIDDEN_OBJECTS_COUNT;
            break;
        case RUBBISH:
            hiddenObjectsCount = RUBBISH_HIDDEN_OBJECTS_COUNT;
            break;
        case AFTERNOONTEA:
            hiddenObjectsCount = AFTERNOONTEA_HIDDEN_OBJECTS_COUNT;
            break;
        default:
            throw new IllegalStateException();
        }
        List<Integer> notUsedObjects = new ArrayList<Integer>(
                hiddenObjectsCount);
        for (int n = 0; n < hiddenObjectsCount; n++) {
            notUsedObjects.add(n);
        }
        Random random = new Random();
        for (int n = 0; n < MAX_OBJECTS_FOR_SEARCH; n++) {
            int randomNumber = random.nextInt(notUsedObjects.size());
            Integer objectId = notUsedObjects.get(randomNumber);
            objectsForSearch[n] = objectId;
            notUsedObjects.remove(randomNumber);
        }
        List<Integer> lst = new ArrayList<Integer>(MAX_OBJECTS_FOR_SEARCH);
        java.util.Collections.addAll(lst, objectsForSearch);
        game.setObjects(lst);
        game.setStarted(true);
        game.setStartTime(Calendar.getInstance());
        notStartedGames.get(hiddenObjectsGameType).remove(gameId);
        games.put(gameId, game);
        sra.setAttribute(HIDDEN_OBJECTS_GAME_STARTED, true,
                ServletRequestAttributes.SCOPE_SESSION);
        return getResult(game);
    }

    public PetFoodDao getPetFoodDao() {
        return petFoodDao;
    }

    public void setPetFoodDao(PetFoodDao petFoodDao) {
        this.petFoodDao = petFoodDao;
    }

    public FoodDao getFoodDao() {
        return foodDao;
    }

    public void setFoodDao(FoodDao foodDao) {
        this.foodDao = foodDao;
    }

    public PetDao getPetDao() {
        return petDao;
    }

    public void setPetDao(PetDao petDao) {
        this.petDao = petDao;
    }

    public PetService getPetService() {
        return petService;
    }

    public void setPetService(PetService petService) {
        this.petService = petService;
    }

    public DrinkDao getDrinkDao() {
        return drinkDao;
    }

    public void setDrinkDao(DrinkDao drinkDao) {
        this.drinkDao = drinkDao;
    }

    public ClothDao getClothDao() {
        return clothDao;
    }

    public void setClothDao(ClothDao clothDao) {
        this.clothDao = clothDao;
    }

    public BookDao getBookDao() {
        return bookDao;
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public RoomDao getRoomDao() {
        return roomDao;
    }

    public void setRoomDao(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public LevelDao getLevelDao() {
        return levelDao;
    }

    public void setLevelDao(LevelDao levelDao) {
        this.levelDao = levelDao;
    }

    public BuildingMaterialDao getBuildingMaterialDao() {
        return buildingMaterialDao;
    }

    public void setBuildingMaterialDao(BuildingMaterialDao buildingMaterialDao) {
        this.buildingMaterialDao = buildingMaterialDao;
    }

    public AchievementDao getAchievementDao() {
        return achievementDao;
    }

    public void setAchievementDao(AchievementDao achievementDao) {
        this.achievementDao = achievementDao;
    }

    public Map<Integer, HiddenObjectsGame> getGames() {
        return games;
    }

    public void setGames(Map<Integer, HiddenObjectsGame> games) {
        this.games = games;
    }

    public Map<Integer, HiddenObjectsGame> getFinishedGames() {
        return finishedGames;
    }

    public void setFinishedGames(Map<Integer, HiddenObjectsGame> finishedGames) {
        this.finishedGames = finishedGames;
    }

    public Map<HiddenObjectsGameType, Map<Integer, HiddenObjectsGame>> getNotStartedGames() {
        return notStartedGames;
    }

    public void setNotStartedGames(
            Map<HiddenObjectsGameType, Map<Integer, HiddenObjectsGame>> notStartedGames) {
        this.notStartedGames = notStartedGames;
    }

    public int getLastGameId() {
        return lastGameId;
    }

    public void setLastGameId(int lastGameId) {
        this.lastGameId = lastGameId;
    }


}
