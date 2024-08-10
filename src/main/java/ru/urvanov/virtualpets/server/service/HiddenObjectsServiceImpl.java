package ru.urvanov.virtualpets.server.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsGameType;
import ru.urvanov.virtualpets.server.controller.api.domain.LevelInfo;
import ru.urvanov.virtualpets.server.dao.BookDao;
import ru.urvanov.virtualpets.server.dao.BuildingMaterialDao;
import ru.urvanov.virtualpets.server.dao.ClothDao;
import ru.urvanov.virtualpets.server.dao.DrinkDao;
import ru.urvanov.virtualpets.server.dao.FoodDao;
import ru.urvanov.virtualpets.server.dao.LevelDao;
import ru.urvanov.virtualpets.server.dao.PetDao;
import ru.urvanov.virtualpets.server.dao.RoomDao;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.AchievementId;
import ru.urvanov.virtualpets.server.dao.domain.Book;
import ru.urvanov.virtualpets.server.dao.domain.Bookcase;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId;
import ru.urvanov.virtualpets.server.dao.domain.Cloth;
import ru.urvanov.virtualpets.server.dao.domain.Drink;
import ru.urvanov.virtualpets.server.dao.domain.DrinkId;
import ru.urvanov.virtualpets.server.dao.domain.Food;
import ru.urvanov.virtualpets.server.dao.domain.FoodId;
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
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.service.domain.HiddenObjectsGameStatus;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@Service
@PreAuthorize("hasRole('USER')")
public class HiddenObjectsServiceImpl implements HiddenObjectsGameService {

    private static final int MAX_OBJECTS_FOR_SEARCH = 8;
    private static final int GAME_TIMEOUT_SECONDS = 30;

    private static final int TREASURY_HIDDEN_OBJECTS_COUNT = 42;
    private static final int RUBBISH_HIDDEN_OBJECTS_COUNT = 52;
    private static final int AFTERNOONTEA_HIDDEN_OBJECTS_COUNT = 56;

    private TreeMap<Float, Cloth> clothDrop = new TreeMap<>();
    private float clothMaxDropRate;
    
    private TreeMap<Float, Book> bookDrop = new TreeMap<>();
    private Map<Integer, Float> bookMaxDropRate = new HashMap<>();
    
    private TreeMap<Float, BuildingMaterial> buildingMaterialDrop = new TreeMap<>();
    private float buildingMaterialMaxDropRate;
    
    private TreeMap<Float, Drink> drinkDrop = new TreeMap<>();
    private Map<Integer, Float> drinkMaxDropRate = new HashMap<>();
    
    private TreeMap<Float, Food> foodDrop = new TreeMap<>();
    private Map<Integer, Float> foodMaxDropRate = new HashMap<>();

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

    private Map<Integer, HiddenObjectsGame> games = new HashMap<Integer, HiddenObjectsGame>();
    private Map<Integer, HiddenObjectsGame> finishedGames = new HashMap<Integer, HiddenObjectsGame>();
    private Map<HiddenObjectsGameType, Map<Integer, HiddenObjectsGame>> notStartedGames = new HashMap<HiddenObjectsGameType, Map<Integer, HiddenObjectsGame>>();
    private int lastGameId;

    @Override
    public synchronized ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsGame joinGame(
            UserPetDetails userPetDetails,
            HiddenObjectsGameStatus hiddenObjectsGameStatus,
            ru.urvanov.virtualpets.server.controller.api.domain.JoinHiddenObjectsGameArg joinHiddenObjectsGameArg)
            throws ServiceException {
        User user = userDao.findById(userPetDetails.getUserId())
                .orElseThrow();
        
        Pet pet = petDao.findById(userPetDetails.getPetId())
                .orElseThrow();

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
        Map<Integer, HiddenObjectsGame> map = notStartedGames.get(joinHiddenObjectsGameArg
                .hiddenObjectsGameType());
        if (map == null) {
            map = new HashMap<Integer, HiddenObjectsGame>();
            notStartedGames.put(joinHiddenObjectsGameArg.hiddenObjectsGameType(), map);
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
        
        hiddenObjectsGameStatus.setId(lastGameId);
        hiddenObjectsGameStatus.setStarted(false);
        hiddenObjectsGameStatus.setOver(false);
        hiddenObjectsGameStatus.setType(joinHiddenObjectsGameArg.hiddenObjectsGameType());

        return getResult(userPetDetails, foundGame);
    }

    private ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsGame getResult(
            UserPetDetails userPetDetails,
            HiddenObjectsGame foundGame) {
        ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsGame result = new ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsGame();
        result.setObjects(foundGame.getObjectsForSearch());
        HiddenObjectsPlayer[] players = foundGame.getDisplayablePlayers();
        for (int n = 0; n < players.length; n++) {
            if (players[n] != null) {
                ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsPlayer resultPlayer = new ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsPlayer();
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
        ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsCollected[] resultCollectedObjects = new ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsCollected[collectedObjects.length];
        for (n = 0; n < collectedObjects.length; n++) {
            int objectId = collectedObjects[n].getObjectId();
            ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsPlayer player = result.getPlayer(collectedObjects[n].getPlayer()
                    .getUserId());
            ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsCollected hoc = new ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsCollected(objectId, player);
            resultCollectedObjects[n] = hoc;
        }
        result.setCollectedObjects(resultCollectedObjects);
        result.setGameStarted(foundGame.isStarted());
        result.setGameOver(foundGame.isGameOver());
        if (foundGame.isGameOver()) {
            Pet pet = petDao.findById(userPetDetails.getPetId())
                    .orElseThrow();
            HiddenObjectsPlayer player = foundGame.getPlayer(pet.getUser()
                    .getId());
            ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsReward resultReward = new ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsReward();
            HiddenObjectsReward playerReward = player.getReward();
            resultReward.setFoodId(playerReward.getFoodId());
            resultReward.setClothId(playerReward.getClothId());
            resultReward.setBookId(playerReward.getBookId());
            resultReward.setDrinkId(playerReward.getDrinkId());
            resultReward.setLevelInfo(playerReward.getLevelInfo());
            resultReward.setExperience(playerReward.getExperience());
            resultReward
                    .setBuildingMaterialId(playerReward.getBuildingMaterialId());
            
            resultReward.setAchievements(playerReward
                    .getAchievements());
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
    public synchronized ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsGame getGameInfo(
            UserPetDetails userPetDetails,
            HiddenObjectsGameStatus hiddenObjectsGameStatus)
            throws ServiceException {
        
//        if (hiddenObjectsGameStatus.isStarted() == null) {
//            hiddenObjectsGameStarted = false;
//        }
//        if (hiddenObjectsGameOver == null) {
//            hiddenObjectsGameOver = null;
//        }
        if (hiddenObjectsGameStatus.getId() == null) {
            throw new ServiceException("No game with such id");
        }
        HiddenObjectsGame foundGame = null;
        Map<Integer, HiddenObjectsGame> map = notStartedGames
                .get(hiddenObjectsGameStatus.getType());
        if (!hiddenObjectsGameStatus.isStarted() && map != null) {
            foundGame = map.get(hiddenObjectsGameStatus.getId());
        }
        if ((foundGame == null) && (!hiddenObjectsGameStatus.isOver())) {
            foundGame = games.get(hiddenObjectsGameStatus.getId());
            if (foundGame != null) {
                Calendar startTime = foundGame.getStartTime();
                Calendar time = Calendar.getInstance();
                time.add(Calendar.SECOND, -GAME_TIMEOUT_SECONDS);
                if (time.after(startTime)) {
                    finishGame(foundGame, hiddenObjectsGameStatus.getId());
                }
            }
        }
        if (foundGame == null) {
            foundGame = finishedGames.get(hiddenObjectsGameStatus.getId());
            if ((hiddenObjectsGameStatus.getId() != null) && (!hiddenObjectsGameStatus.isOver())) {
                hiddenObjectsGameStatus.setOver(true);
            }
        } else {
            if (foundGame.isStarted() != hiddenObjectsGameStatus.isStarted()) {
                hiddenObjectsGameStatus.setStarted(foundGame.isStarted());
            }
            if (foundGame.isGameOver() != hiddenObjectsGameStatus.isOver()) {
                hiddenObjectsGameStatus.setOver(foundGame.isGameOver());
            }
        }
        return getResult(userPetDetails, foundGame);
    }

    @Override
    public synchronized ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsGame collectObject(
            UserPetDetails userPetDetails,
            HiddenObjectsGameStatus hiddenObjectsGameStatus,
            ru.urvanov.virtualpets.server.controller.api.domain.CollectObjectArg arg)
            throws ServiceException {

        Integer userId = userPetDetails.getUserId();

//        if (hiddenObjectsGameStarted == null) {
//            hiddenObjectsGameStarted = false;
//        }
        if (hiddenObjectsGameStatus.getId() == null) {
            throw new ServiceException("No game with such id");
        }
        HiddenObjectsGame foundGame = null;
        if (!hiddenObjectsGameStatus.isStarted()) {
            throw new ServiceException("Game not started.");
        }
        if (foundGame == null) {
            foundGame = games.get(hiddenObjectsGameStatus.getId());
        }
        if (foundGame != null) {
            Integer collectObjectId = arg.objectId();
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
                            finishGame(foundGame, hiddenObjectsGameStatus.getId());
                        }
                    }
                }
            }
        } else {
            foundGame = finishedGames.get(hiddenObjectsGameStatus.getId());
        }
        return getResult(userPetDetails, foundGame);
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
                FoodId foodId = FoodId.DRY_FOOD;
                String clothId = null;
                BuildingMaterialId buildingMaterialId = null;
                String bookId = null;
                DrinkId drinkId = null;
                if (score > MAX_OBJECTS_FOR_SEARCH
                        / HiddenObjectsGame.MAX_PLAYERS_COUNT) {
                    if (game.getObjectsForSearchCount() == 0) {
                        Random random = new Random();
                        random.nextInt(MAX_OBJECTS_FOR_SEARCH);
                        Room room = roomDao.findByPetId(player.getPetId())
                                .orElseThrow();
                        Refrigerator refrigerator = room.getRefrigerator();
                        MachineWithDrinks machineWithDrinks = room
                                .getMachineWithDrinks();
                        Bookcase bookcase = room.getBookcase();
                        if (refrigerator != null) {
                            foodId = calculateFoodDrop(random, refrigerator.getId()).getId();
                        }
                        if (random.nextInt(100) < 100) {
                            clothId = calculateClothDrop(random).getId();
                        }
                        if (random.nextInt(100) < 300) {
                            buildingMaterialId = calculateBuildingMaterialDrop(random).getId();
                        }
                        if (machineWithDrinks != null) {
                            drinkId = calculateDrinkDrop(random, machineWithDrinks.getId()).getId();
                        }
                        if (bookcase != null && random.nextInt(100) < 100) {
                            bookId = calculateBookDrop(random, bookcase.getId()).getId();
                        }
                    }
                }
                HiddenObjectsReward reward = new HiddenObjectsReward();

                reward.setFoodId(foodId);

                reward.setBuildingMaterialId(buildingMaterialId);
                reward.setDrinkId(drinkId);
                reward.setBookId(bookId);
                player.setReward(reward);

                Pet fullPet = petDao.findFullById(player.getPetId())
                        .orElseThrow();
                if (!fullPet.getFoods().containsKey(foodId)) {
                    PetFood food = new PetFood();
                    food.setPet(fullPet);
                    food.setFood(foodDao.getReference(foodId));
                    food.setFoodCount(1);
                    fullPet.getFoods().put(foodId, food);
                } else {
                    PetFood food = fullPet.getFoods().get(foodId);
                    food.setFoodCount(food.getFoodCount() + 1);
                }
                
                fullPet.setMood(100);
                Set<Cloth> cloths = fullPet.getCloths();
                boolean clothFound = false;
                for (Cloth cloth : cloths) {
                    if (cloth.getId().equals(clothId)) {
                        clothFound = true;
                    }
                }
                if ((!clothFound) && (clothId != null)) {
                    cloths.add(clothDao.findById(clothId).orElseThrow());
                    reward.setClothId(clothId);
                }
                if (buildingMaterialId != null) {
                    Map<BuildingMaterialId, PetBuildingMaterial> mapBuildingMaterials = fullPet
                            .getBuildingMaterials();
                    PetBuildingMaterial petBuildingMaterial = mapBuildingMaterials
                            .get(buildingMaterialId);
                    if (petBuildingMaterial == null) {
                        petBuildingMaterial = new PetBuildingMaterial();
                        petBuildingMaterial.setBuildingMaterial(buildingMaterialDao.getReference(buildingMaterialId));
                        petBuildingMaterial.setPet(fullPet);
                        petBuildingMaterial.setBuildingMaterialCount(0);
                        fullPet.getBuildingMaterials().put(buildingMaterialId,
                                petBuildingMaterial);
                    }
                    petBuildingMaterial
                            .setBuildingMaterialCount(petBuildingMaterial
                                    .getBuildingMaterialCount() + 1);
                }
                if (drinkId != null) {
                    Map<DrinkId, PetDrink> mapDrinks = fullPet.getDrinks();
                    PetDrink petDrink = mapDrinks.get(drinkId);
                    if (petDrink == null) {
                        petDrink = new PetDrink();
                        petDrink.setDrink(drinkDao.getReference(drinkId));
                        petDrink.setPet(fullPet);
                        petDrink.setDrinkCount(0);
                        fullPet.getDrinks().put(drinkId, petDrink);
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
                if (fullPet.getHiddenObjectsGameCount() == 1)
                    petService.addAchievementIfNot(fullPet,
                            AchievementId.HIDDEN_OBJECTS_GAME_1);
                if (fullPet.getHiddenObjectsGameCount() == 10)
                    petService.addAchievementIfNot(fullPet,
                            AchievementId.HIDDEN_OBJECTS_GAME_10);
                if (fullPet.getHiddenObjectsGameCount() == 100)
                    petService.addAchievementIfNot(fullPet,
                            AchievementId.HIDDEN_OBJECTS_GAME_100);

                
                int experienceReward = 1;
                petService.addExperience(fullPet, experienceReward);

                Level nextLevel = levelDao.findById(fullPet.getLevel()
                        .getId() + 1).orElseThrow();
                int maxExperience;
                if (nextLevel != null) {
                    maxExperience = nextLevel.getExperience();
                } else {
                    maxExperience = Integer.MAX_VALUE;
                }
                LevelInfo levelInfo = new LevelInfo(fullPet.getLevel().getId(), fullPet.getExperience(), fullPet.getLevel().getExperience(), maxExperience);
                reward.setExperience(experienceReward);
                reward.setLevelInfo(levelInfo);

                ru.urvanov.virtualpets.server.dao.domain.AchievementId[] achievements = petService
                        .calculateAchievements(fullPet)
                        .toArray(
                                new ru.urvanov.virtualpets.server.dao.domain.AchievementId[0]);

                reward.setAchievements(achievements);
                petDao.save(fullPet);
            }
        }
    }

    private Food calculateFoodDrop(Random random, Integer refrigeratorLevel) {
        float randomNumber = random.nextFloat(this.foodMaxDropRate.get(refrigeratorLevel));
        Entry<Float, Food> entry = foodDrop.floorEntry(randomNumber);
        return entry.getValue();
    }

    private Drink calculateDrinkDrop(Random random, int machineWithDrinksLevel) {
        float randomNumber = random.nextFloat(this.drinkMaxDropRate.get(machineWithDrinksLevel));
        Entry<Float, Drink> entry = drinkDrop.floorEntry(randomNumber);
        return entry.getValue();
    }

    private BuildingMaterial calculateBuildingMaterialDrop(Random random) {
        float randomNumber = random.nextFloat(buildingMaterialMaxDropRate);
        Entry<Float, BuildingMaterial> entry = buildingMaterialDrop.floorEntry(randomNumber);
        return entry.getValue();
    }

    private Book calculateBookDrop(Random random, int bookcaseLevel) {
        float randomNumber = random.nextFloat(bookMaxDropRate.get(bookcaseLevel));
        Entry<Float, Book> entry = bookDrop.floorEntry(randomNumber);
        return entry.getValue();
    }

    private Cloth calculateClothDrop(Random random) {
        float randomNumber = random.nextFloat(clothMaxDropRate);
        Entry<Float, Cloth> entry = clothDrop.floorEntry(randomNumber);
        return entry.getValue();
    }

    @Override
    public synchronized void leaveGame(UserPetDetails userPetDetails,
            HiddenObjectsGameStatus hiddenObjectsGameStatus) {

        notStartedGames.get(hiddenObjectsGameStatus.getType()).remove(hiddenObjectsGameStatus.getId());
        games.remove(hiddenObjectsGameStatus.getId());
    }

    @Override
    public synchronized ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsGame startGame(
            UserPetDetails userPetDetails,
            HiddenObjectsGameStatus hiddenObjectsGameStatus)
            throws ServiceException {
//        if (hiddenObjectsGameStarted == null) {
//            hiddenObjectsGameStarted = false;
//        }
        HiddenObjectsGame game = null;
        Map<Integer, HiddenObjectsGame> map = notStartedGames
                .get(hiddenObjectsGameStatus.getType());
        if (!hiddenObjectsGameStatus.isStarted() && map != null) {
            game = map.get(hiddenObjectsGameStatus.getId());
        }
        if (game == null) {
            game = games.get(hiddenObjectsGameStatus.getId());
            if (game != null) {
                return getResult(userPetDetails, game);
            }
        }
        if (game == null) {
            throw new ServiceException("No game found with such id.");
        }

        Integer[] objectsForSearch = new Integer[MAX_OBJECTS_FOR_SEARCH];

        int hiddenObjectsCount;
        switch (hiddenObjectsGameStatus.getType()) {
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
        notStartedGames.get(hiddenObjectsGameStatus.getType()).remove(hiddenObjectsGameStatus.getId());
        games.put(hiddenObjectsGameStatus.getId(), game);
        return getResult(userPetDetails, game);
    }
    
    @PostConstruct
    public void init() {
        initClothDrop();
        initBookDrop();
        initBuildingMaterialDrop();
        initDrinkDrop();
        initFoodDrop();
    }

    private void initFoodDrop() {
        List<Food> foods = foodDao.findAllOrderByRefrigeratorLevelAndRefrigeratorOrder();
        float rate = 0.0f;
        for (Food food: foods) {
            foodDrop.put(rate,  food);
            foodMaxDropRate.compute(
                    food.getRefrigeratorLevel(),
                    (k, f) -> f == null ? food.getHiddenObjectsGameDropRate()
                            : f + food.getHiddenObjectsGameDropRate());
            rate += food.getHiddenObjectsGameDropRate();
        }
    }

    private void initDrinkDrop() {
        List<Drink> drinks = drinkDao.findAllOrderByMachineWithDrinksLevelAndMachineWithDrinksOrder();
        float rate = 0.0f;
        for (Drink drink : drinks) {
            drinkDrop.put(rate, drink);
            drinkMaxDropRate.compute(
                    drink.getMachineWithDrinksLevel(),
                    (k, d) -> d == null ? drink.getHiddenObjectsGameDropRate()
                            : d + drink.getHiddenObjectsGameDropRate());
            
            rate += drink.getHiddenObjectsGameDropRate();
        }
    }

    private void initBuildingMaterialDrop() {
        List<BuildingMaterial> buildingMaterials = buildingMaterialDao.findAll();
        float rate = 0.0f;
        for (BuildingMaterial buildingMaterial : buildingMaterials) {
            buildingMaterialDrop.put(rate, buildingMaterial);
            rate += buildingMaterial.getHiddenObjectsGameDropRate();
        }
        buildingMaterialMaxDropRate = rate;
    }

    private void initBookDrop() {
        List<Book> books = bookDao.findAllOrderByBookcaseLevelAndBookcaseOrder();
        float rate = 0.0f;
        for (Book book : books) {
            bookDrop.put(rate,  book);
            bookMaxDropRate.compute(
                    book.getBookcaseLevel(),
                    (k, b) -> b == null ? book.getHiddenObjectsGameDropRate()
                            : b + book.getHiddenObjectsGameDropRate());
            
            rate += book.getHiddenObjectsGameDropRate();
        }
    }

    private void initClothDrop() {
        List<Cloth> cloths = clothDao.findAll();
        float rate = 0.0f;
        for (Cloth cloth : cloths) {
            clothDrop.put(rate, cloth);
            rate += cloth.getHiddenObjectsGameDropRate();
        }
        clothMaxDropRate = rate;
    }

}
