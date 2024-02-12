/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;
import java.util.Map;

/**
 * @author fedya
 *
 */
public class GetPetFoodsResult implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2119506370052876533L;


    private Map<FoodType, Integer> foodCounts;


    /**
     * @return the foodCounts
     */
    public Map<FoodType, Integer> getFoodCounts() {
        return foodCounts;
    }


    /**
     * @param foodCounts the foodCounts to set
     */
    public void setFoodCounts(Map<FoodType, Integer> foodCounts) {
        this.foodCounts = foodCounts;
    }
}
