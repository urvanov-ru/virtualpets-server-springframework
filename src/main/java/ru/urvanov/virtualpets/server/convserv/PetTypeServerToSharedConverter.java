package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

public class PetTypeServerToSharedConverter implements Converter<ru.urvanov.virtualpets.server.dao.domain.PetType, ru.urvanov.virtualpets.shared.domain.PetType> {

    @Override
    public ru.urvanov.virtualpets.shared.domain.PetType convert(
            ru.urvanov.virtualpets.server.dao.domain.PetType arg0) {
        switch (arg0) {
        case CAT:
            return ru.urvanov.virtualpets.shared.domain.PetType.CAT;
        //case TUX:
        //    return ru.urvanov.virtualpets.shared.domain.PetType.TUX;
        //case KONQI:
        //    return ru.urvanov.virtualpets.shared.domain.PetType.KONQI;
        }
        return null;
    }
}
