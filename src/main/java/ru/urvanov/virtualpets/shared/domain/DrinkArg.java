/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

/**
 * @author fedya
 *
 */
public class DrinkArg implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 3060092977876903946L;
    private DrinkType drinkType;
    public DrinkType getDrinkType() {
        return drinkType;
    }
    public void setDrinkType(DrinkType drinkType) {
        this.drinkType = drinkType;
    }

}
