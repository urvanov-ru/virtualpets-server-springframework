package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

public class BuildingMaterialTypeSharedToServerConverter implements Converter<ru.urvanov.virtualpets.server.api.domain.BuildingMaterialType, ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId> {

    @Override
    public ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId convert(
            ru.urvanov.virtualpets.server.api.domain.BuildingMaterialType source) {
        return ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId.valueOf(source.name());
    }

}
