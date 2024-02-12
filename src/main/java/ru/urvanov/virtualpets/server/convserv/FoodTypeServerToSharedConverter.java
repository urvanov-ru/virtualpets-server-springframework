/**
 * 
 */
package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.shared.domain.FoodType;

/**
 * @author fedya
 *
 */
public class FoodTypeServerToSharedConverter implements Converter<ru.urvanov.virtualpets.server.dao.domain.FoodType, ru.urvanov.virtualpets.shared.domain.FoodType> {

    @Override
    public FoodType convert(
            ru.urvanov.virtualpets.server.dao.domain.FoodType source) {
        return ru.urvanov.virtualpets.shared.domain.FoodType.valueOf(source.name());
    }

}
