/**
 * 
 */
package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.server.api.domain.DrinkType;

/**
 * @author fedya
 *
 */
public class DrinkTypeServerToSharedConverter implements Converter<ru.urvanov.virtualpets.server.dao.domain.DrinkId, ru.urvanov.virtualpets.server.api.domain.DrinkType> {

    @Override
    public DrinkType convert(
            ru.urvanov.virtualpets.server.dao.domain.DrinkId source) {
        return ru.urvanov.virtualpets.server.api.domain.DrinkType.valueOf(source.name());
    }

}
