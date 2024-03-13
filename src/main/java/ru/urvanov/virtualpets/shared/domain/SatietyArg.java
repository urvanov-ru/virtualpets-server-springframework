package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

public class SatietyArg implements Serializable {

    private static final long serialVersionUID = 3060092977876903946L;
    private FoodType foodType;

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }
    
}
