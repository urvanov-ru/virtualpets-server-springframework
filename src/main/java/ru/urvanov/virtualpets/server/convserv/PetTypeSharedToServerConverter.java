package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

public class PetTypeSharedToServerConverter implements Converter<ru.urvanov.virtualpets.shared.domain.PetType, ru.urvanov.virtualpets.server.dao.domain.PetType>  {

    @Override
    public ru.urvanov.virtualpets.server.dao.domain.PetType convert(
            ru.urvanov.virtualpets.shared.domain.PetType arg0) {
        switch (arg0) {
        case CAT:
            return ru.urvanov.virtualpets.server.dao.domain.PetType.CAT;
        //case TUX:
        //    return ru.urvanov.virtualpets.server.dao.domain.PetType.TUX;
        //case KONQI:
        //    return ru.urvanov.virtualpets.server.dao.domain.PetType.KONQI;
        }
        return null;
    }
}
