package ru.urvanov.virtualpets.server.dao.domain;

import ru.urvanov.virtualpets.shared.domain.LevelInfo;

public class HiddenObjectsReward {
    private FoodId food;
    private String clothId;
    private LevelInfo levelInfo;
    private int experience;
    private BuildingMaterialId buildingMaterialType;
    private String bookId;
    private DrinkId drinkType;
    private AchievementId[] achievements;

    public FoodId getFood() {
        return food;
    }

    public void setFood(FoodId food) {
        this.food = food;
    }

    public String getClothId() {
        return clothId;
    }

    public void setClothId(String clothId) {
        this.clothId = clothId;
    }


    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public LevelInfo getLevelInfo() {
        return levelInfo;
    }

    public void setLevelInfo(LevelInfo levelInfo) {
        this.levelInfo = levelInfo;
    }

    public BuildingMaterialId getBuildingMaterialType() {
        return buildingMaterialType;
    }

    public void setBuildingMaterialType(BuildingMaterialId buildingMaterialType) {
        this.buildingMaterialType = buildingMaterialType;
    }

    public AchievementId[] getAchievements() {
        return achievements;
    }

    public void setAchievements(AchievementId[] achievements) {
        this.achievements = achievements;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public DrinkId getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(DrinkId drinkType) {
        this.drinkType = drinkType;
    }

}
