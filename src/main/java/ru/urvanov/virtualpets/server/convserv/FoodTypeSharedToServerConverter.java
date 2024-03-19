/**
 * 
 */
package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.server.dao.domain.FoodId;

/**
 * @author fedya
 *
 */
public class FoodTypeSharedToServerConverter implements Converter<ru.urvanov.virtualpets.shared.domain.FoodType, ru.urvanov.virtualpets.server.dao.domain.FoodId> {

    @Override
    public FoodId convert(
            ru.urvanov.virtualpets.shared.domain.FoodType source) {
        return ru.urvanov.virtualpets.server.dao.domain.FoodId.valueOf(source.name());
    }

}
