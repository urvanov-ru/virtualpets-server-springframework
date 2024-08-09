package ru.urvanov.virtualpets.server.dao.domain;

import ru.urvanov.virtualpets.server.controller.api.domain.LevelInfo;

public class HiddenObjectsReward {
    private FoodId foodId;
    private String clothId;
    private LevelInfo levelInfo;
    private int experience;
    private BuildingMaterialId buildingMaterialId;
    private String bookId;
    private DrinkId drinkId;
    private AchievementId[] achievements;

    public FoodId getFoodId() {
        return foodId;
    }

    public void setFoodId(FoodId foodId) {
        this.foodId = foodId;
    }

    public String getClothId() {
        return clothId;
    }

    public void setClothId(String clothId) {
        this.clothId = clothId;
    }

    public LevelInfo getLevelInfo() {
        return levelInfo;
    }

    public void setLevelInfo(LevelInfo levelInfo) {
        this.levelInfo = levelInfo;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public BuildingMaterialId getBuildingMaterialId() {
        return buildingMaterialId;
    }

    public void setBuildingMaterialId(
            BuildingMaterialId buildingMaterialId) {
        this.buildingMaterialId = buildingMaterialId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public DrinkId getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(DrinkId drinkId) {
        this.drinkId = drinkId;
    }

    public AchievementId[] getAchievements() {
        return achievements;
    }

    public void setAchievements(AchievementId[] achievements) {
        this.achievements = achievements;
    }

}
