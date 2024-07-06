package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.server.api.domain.Food;
import ru.urvanov.virtualpets.server.dao.domain.PetFood;

public class FoodToApiConverter implements Converter<PetFood, ru.urvanov.virtualpets.server.api.domain.Food> {

    @Override
    public Food convert(PetFood source) {
        return new ru.urvanov.virtualpets.server.api.domain.Food(
                source.getFood().getId(),
                source.getFood().getRefrigeratorLevel(),
                source.getFood().getRefrigeratorOrder(),
                source.getFoodCount());
    }



}
