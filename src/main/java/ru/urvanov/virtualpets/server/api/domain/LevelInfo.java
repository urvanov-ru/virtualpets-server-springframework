package ru.urvanov.virtualpets.server.api.domain;

public record LevelInfo(Integer level, Integer experience,
        Integer minExperience, Integer maxExperience) {
};
