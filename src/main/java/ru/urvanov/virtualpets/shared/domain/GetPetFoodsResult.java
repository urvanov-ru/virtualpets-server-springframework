package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GetPetFoodsResult implements Serializable {
    private static final long serialVersionUID = -2119506370052876533L;

    private List<Food> foods = new ArrayList<>();

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }
    
}
