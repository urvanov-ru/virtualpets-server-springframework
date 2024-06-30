package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

public class SexServerToSharedConverter
        implements
        Converter<ru.urvanov.virtualpets.server.dao.domain.Sex, ru.urvanov.virtualpets.server.api.domain.Sex> {

    @Override
    public ru.urvanov.virtualpets.server.api.domain.Sex convert(
            ru.urvanov.virtualpets.server.dao.domain.Sex source) {
        if (source == null) {
        }
        switch (source) {
        case MAN:
            return ru.urvanov.virtualpets.server.api.domain.Sex.MAN;
        case WOMAN:
            return ru.urvanov.virtualpets.server.api.domain.Sex.WOMAN;
        }
        return null;
    }

}
