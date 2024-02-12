package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

public class ClothTypeSharedToServerConverter implements Converter<ru.urvanov.virtualpets.shared.domain.ClothType, ru.urvanov.virtualpets.server.dao.domain.ClothType> {

    @Override
    public ru.urvanov.virtualpets.server.dao.domain.ClothType convert(
            ru.urvanov.virtualpets.shared.domain.ClothType source) {
        return ru.urvanov.virtualpets.server.dao.domain.ClothType.valueOf(source.name());
    }

}
