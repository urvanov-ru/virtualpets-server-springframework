/**
 * 
 */
package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

/**
 * @author fedya
 *
 */
public class BuildingMaterialTypeServerToSharedConverter implements Converter<ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId, ru.urvanov.virtualpets.server.api.domain.BuildingMaterialType> {

    @Override
    public ru.urvanov.virtualpets.server.api.domain.BuildingMaterialType convert(
            ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId source) {
        return ru.urvanov.virtualpets.server.api.domain.BuildingMaterialType.valueOf(source.name());
    }

}
