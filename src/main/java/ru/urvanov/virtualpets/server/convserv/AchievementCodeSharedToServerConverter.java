package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.server.dao.domain.AchievementId;

public class AchievementCodeSharedToServerConverter implements Converter<ru.urvanov.virtualpets.shared.domain.AchievementCode, ru.urvanov.virtualpets.server.dao.domain.AchievementId> {

    @Override
    public AchievementId convert(
            ru.urvanov.virtualpets.shared.domain.AchievementCode source) {
        return source == null ? null : ru.urvanov.virtualpets.server.dao.domain.AchievementId.values()[source.ordinal()];
    }

}
