package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GetPetDrinksResult implements Serializable {

    private static final long serialVersionUID = -2119506370052876533L;
    
    private List<Drink> drinks = new ArrayList<>();

    public List<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }
    
    
}
