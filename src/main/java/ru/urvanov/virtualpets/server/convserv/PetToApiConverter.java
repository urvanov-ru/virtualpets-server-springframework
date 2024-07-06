package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.server.api.domain.PetInfo;
import ru.urvanov.virtualpets.server.dao.domain.Pet;

public class PetToApiConverter implements Converter<Pet, PetInfo> {

    @Override
    public PetInfo convert(Pet source) {
        return new PetInfo(source.getId(), source.getName(),
                source.getPetType());
    }

}
