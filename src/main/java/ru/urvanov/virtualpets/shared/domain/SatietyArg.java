/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

/**
 * @author fedya
 *
 */
public class SatietyArg implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 3060092977876903946L;
    private FoodType foodType;

    /**
     * @return the foodType
     */
    public FoodType getFoodType() {
        return foodType;
    }

    /**
     * @param foodType the foodType to set
     */
    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }
    
}
