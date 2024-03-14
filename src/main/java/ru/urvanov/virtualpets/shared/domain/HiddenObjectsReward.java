package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

public class HiddenObjectsReward implements Serializable {

    private static final long serialVersionUID = 7176544599291504383L;
    private FoodType food;
    private String clothId;
    private LevelInfo levelInfo;
    private int experience;
    private BuildingMaterialType buildingMaterialType;
    private AchievementCode[] achievements;
    private String bookId;
    private DrinkType drinkType;

    public FoodType getFood() {
        return food;
    }

    public void setFood(FoodType food) {
        this.food = food;
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

    public BuildingMaterialType getBuildingMaterialType() {
        return buildingMaterialType;
    }

    public void setBuildingMaterialType(BuildingMaterialType buildingMaterialType) {
        this.buildingMaterialType = buildingMaterialType;
    }

    public AchievementCode[] getAchievements() {
        return achievements;
    }

    public void setAchievements(AchievementCode[] achievements) {
        this.achievements = achievements;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public DrinkType getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(DrinkType drinkType) {
        this.drinkType = drinkType;
    }
    

}
