package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.shared.domain.AchievementCode;

public class AchievementCodeServerToSharedConverter
        implements
        Converter<ru.urvanov.virtualpets.server.dao.domain.AchievementCode, ru.urvanov.virtualpets.shared.domain.AchievementCode> {

    @Override
    public AchievementCode convert(
            ru.urvanov.virtualpets.server.dao.domain.AchievementCode source) {
        return source == null ? null
                : ru.urvanov.virtualpets.shared.domain.AchievementCode
                        .values()[source.ordinal()];
    }

}
