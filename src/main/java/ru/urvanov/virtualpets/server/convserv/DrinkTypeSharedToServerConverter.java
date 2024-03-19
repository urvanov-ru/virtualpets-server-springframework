/**
 * 
 */
package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.server.dao.domain.DrinkId;

/**
 * @author fedya
 *
 */
public class DrinkTypeSharedToServerConverter implements Converter<ru.urvanov.virtualpets.shared.domain.DrinkType, ru.urvanov.virtualpets.server.dao.domain.DrinkId> {

    @Override
    public DrinkId convert(
            ru.urvanov.virtualpets.shared.domain.DrinkType source) {
        return ru.urvanov.virtualpets.server.dao.domain.DrinkId.valueOf(source.name());
    }

}
