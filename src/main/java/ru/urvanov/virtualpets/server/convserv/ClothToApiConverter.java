package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.server.dao.domain.Cloth;

public class ClothToApiConverter implements
        Converter<Cloth, ru.urvanov.virtualpets.server.api.domain.Cloth> {

    @Override
    public ru.urvanov.virtualpets.server.api.domain.Cloth convert(
            Cloth source) {
        return new ru.urvanov.virtualpets.server.api.domain.Cloth(
                source.getId(),
                source.getClothType(),
                source.getWardrobeOrder());
    }

}
