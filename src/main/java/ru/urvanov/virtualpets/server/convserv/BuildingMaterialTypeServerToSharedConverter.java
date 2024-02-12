/**
 * 
 */
package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

/**
 * @author fedya
 *
 */
public class BuildingMaterialTypeServerToSharedConverter implements Converter<ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialType, ru.urvanov.virtualpets.shared.domain.BuildingMaterialType> {

    @Override
    public ru.urvanov.virtualpets.shared.domain.BuildingMaterialType convert(
            ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialType source) {
        return ru.urvanov.virtualpets.shared.domain.BuildingMaterialType.valueOf(source.name());
    }

}
