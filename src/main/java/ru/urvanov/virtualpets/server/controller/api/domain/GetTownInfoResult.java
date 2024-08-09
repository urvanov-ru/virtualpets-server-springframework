package ru.urvanov.virtualpets.server.controller.api.domain;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.AchievementId;

public record GetTownInfoResult(long newJournalEntriesCount,
        LevelInfo levelInfo, List<AchievementId> achievements) {
};
