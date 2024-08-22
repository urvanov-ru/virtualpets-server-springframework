package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.server.dao.domain.Cloth;

/**
 * На основе экземпляра класса Cloth предметной области создаёт
 * экземпляр Cloth из API для JavaScript-клиента.
 */
public class ClothToApiConverter implements Converter<Cloth,
        ru.urvanov.virtualpets.server.controller.api.domain.Cloth> {

    /**
     * @param source Экземпляр {@link Cloth} предметной области.
     * @return Экземпляр {@link
     * ru.urvanov.virtualpets.server.controller.api.domain.Cloth} API
     */
    @Override
    public ru.urvanov.virtualpets.server.controller.api.domain.Cloth convert(
            Cloth source) {
        return new ru.urvanov.virtualpets.server.controller.api.domain.Cloth(
                source.getId(),
                source.getClothType(),
                source.getWardrobeOrder());
    }

}
