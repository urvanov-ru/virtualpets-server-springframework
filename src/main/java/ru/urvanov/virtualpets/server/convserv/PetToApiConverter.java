package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.server.controller.api.domain.PetInfo;
import ru.urvanov.virtualpets.server.dao.domain.Pet;

/**
 * На основе экземпляра класса Pet предметной области создаёт
 * экземпляр Pet из API для JavaScript-клиента.
 */
public class PetToApiConverter implements Converter<Pet, PetInfo> {

    /**
     * @param source Экземпляр {@link Pet} предметной области.
     * @return Экземпляр {@link
     * ru.urvanov.virtualpets.server.controller.api.domain.Pet} API
     */
    @Override
    public PetInfo convert(Pet source) {
        return new PetInfo(source.getId(), source.getName(),
                source.getPetType());
    }

}
