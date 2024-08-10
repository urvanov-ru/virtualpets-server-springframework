package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.server.dao.domain.PetDrink;

public class DrinkToApiConverter implements Converter<PetDrink, ru.urvanov.virtualpets.server.controller.api.domain.Drink> {

    @Override
    public ru.urvanov.virtualpets.server.controller.api.domain.Drink convert(
            PetDrink source) {
        return new ru.urvanov.virtualpets.server.controller.api.domain.Drink(
                source.getDrink().getId(),
                source.getDrink().getMachineWithDrinksLevel(),
                source.getDrink().getMachineWithDrinksOrder(),
                source.getDrinkCount());
    }

}
