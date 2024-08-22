package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.server.controller.api.domain.Food;
import ru.urvanov.virtualpets.server.dao.domain.PetFood;

/**
 * На основе экземпляра класса Food предметной области создаёт
 * экземпляр Food из API для JavaScript-клиента.
 */
public class FoodToApiConverter implements Converter<PetFood,
        ru.urvanov.virtualpets.server.controller.api.domain.Food> {

    /**
     * @param source Экземпляр {@link Food} предметной области.
     * @return Экземпляр {@link
     * ru.urvanov.virtualpets.server.controller.api.domain.Food} API
     */
    @Override
    public Food convert(PetFood source) {
        return new ru.urvanov.virtualpets.server.controller.api.domain.Food(
                source.getFood().getId(),
                source.getFood().getRefrigeratorLevel(),
                source.getFood().getRefrigeratorOrder(),
                source.getFoodCount());
    }



}
