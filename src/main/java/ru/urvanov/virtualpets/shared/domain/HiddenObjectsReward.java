/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

/**
 * @author fedya
 *
 */
public class HiddenObjectsReward implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 7176544599291504383L;
    private FoodType food;
    private Integer clothId;
    private LevelInfo levelInfo;
    private int experience;
    private BuildingMaterialType buildingMaterialType;
    private AchievementCode[] achievements;
    private Integer bookId;
    private DrinkType drinkType;

    /**
     * @return the food
     */
    public FoodType getFood() {
        return food;
    }

    /**
     * @param food the food to set
     */
    public void setFood(FoodType food) {
        this.food = food;
    }

    /**
     * @return the clothId
     */
    public Integer getClothId() {
        return clothId;
    }

    /**
     * @param clothId the clothId to set
     */
    public void setClothId(Integer clothId) {
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

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public DrinkType getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(DrinkType drinkType) {
        this.drinkType = drinkType;
    }
    

}
