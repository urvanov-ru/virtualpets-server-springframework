package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

public class RoleServerToSharedConverter implements Converter<ru.urvanov.virtualpets.server.dao.domain.Role, ru.urvanov.virtualpets.shared.domain.Role> {

    @Override
    public ru.urvanov.virtualpets.shared.domain.Role convert(
            ru.urvanov.virtualpets.server.dao.domain.Role arg0) {
        switch (arg0) {
        case USER:
            return ru.urvanov.virtualpets.shared.domain.Role.USER;
        case PRIVILEGED_USER:
            return ru.urvanov.virtualpets.shared.domain.Role.PRIVILEGED_USER;
        case VIP:
            return ru.urvanov.virtualpets.shared.domain.Role.VIP;
        case ADMINISTRATOR:
            return ru.urvanov.virtualpets.shared.domain.Role.ADMINISTRATOR;
        case MAIN_ADMINISTRATOR:
            return ru.urvanov.virtualpets.shared.domain.Role.MAIN_ADMINISTRATOR;
        }
        return null;
    }
}
