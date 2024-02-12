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
public class GetPetDrinksResult implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2119506370052876533L;


    private Map<DrinkType, Integer> drinkCounts;


    public Map<DrinkType, Integer> getDrinkCounts() {
        return drinkCounts;
    }


    public void setDrinkCounts(Map<DrinkType, Integer> drinkCounts) {
        this.drinkCounts = drinkCounts;
    }



}
