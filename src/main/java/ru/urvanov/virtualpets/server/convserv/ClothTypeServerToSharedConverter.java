/**
 * 
 */
package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

/**
 * @author fedya
 *
 */
public class ClothTypeServerToSharedConverter implements Converter<ru.urvanov.virtualpets.server.dao.domain.ClothType, ru.urvanov.virtualpets.shared.domain.ClothType> {

    @Override
    public ru.urvanov.virtualpets.shared.domain.ClothType convert(
            ru.urvanov.virtualpets.server.dao.domain.ClothType source) {
        return ru.urvanov.virtualpets.shared.domain.ClothType.valueOf(source.name());
    }

}
