package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

public class RoleSharedToServerConverter implements Converter<ru.urvanov.virtualpets.server.dao.domain.Sex, ru.urvanov.virtualpets.server.api.domain.Sex> {

    @Override
    public ru.urvanov.virtualpets.server.api.domain.Sex convert(ru.urvanov.virtualpets.server.dao.domain.Sex arg0) {
        switch (arg0) {
        case MAN:
            return ru.urvanov.virtualpets.server.api.domain.Sex.MAN;
        case WOMAN:
            return ru.urvanov.virtualpets.server.api.domain.Sex.WOMAN;
        }
        return null;
    }
}
