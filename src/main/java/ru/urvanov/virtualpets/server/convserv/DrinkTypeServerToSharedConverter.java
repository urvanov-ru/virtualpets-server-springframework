/**
 * 
 */
package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.shared.domain.DrinkType;

/**
 * @author fedya
 *
 */
public class DrinkTypeServerToSharedConverter implements Converter<ru.urvanov.virtualpets.server.dao.domain.DrinkId, ru.urvanov.virtualpets.shared.domain.DrinkType> {

    @Override
    public DrinkType convert(
            ru.urvanov.virtualpets.server.dao.domain.DrinkId source) {
        return ru.urvanov.virtualpets.shared.domain.DrinkType.valueOf(source.name());
    }

}
