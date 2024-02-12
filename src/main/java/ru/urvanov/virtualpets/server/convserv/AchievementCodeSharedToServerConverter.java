package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.server.dao.domain.AchievementCode;

public class AchievementCodeSharedToServerConverter implements Converter<ru.urvanov.virtualpets.shared.domain.AchievementCode, ru.urvanov.virtualpets.server.dao.domain.AchievementCode> {

    @Override
    public AchievementCode convert(
            ru.urvanov.virtualpets.shared.domain.AchievementCode source) {
        return source == null ? null : ru.urvanov.virtualpets.server.dao.domain.AchievementCode.values()[source.ordinal()];
    }

}
