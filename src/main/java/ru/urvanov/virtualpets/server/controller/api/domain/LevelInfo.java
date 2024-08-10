package ru.urvanov.virtualpets.server.controller.api.domain;

public record LevelInfo(Integer level, Integer experience,
        Integer minExperience, Integer maxExperience) {
};
