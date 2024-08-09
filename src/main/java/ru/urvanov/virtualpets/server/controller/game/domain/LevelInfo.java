package ru.urvanov.virtualpets.server.controller.game.domain;

public record LevelInfo(Integer level, Integer experience,
        Integer minExperience, Integer maxExperience) {
};
