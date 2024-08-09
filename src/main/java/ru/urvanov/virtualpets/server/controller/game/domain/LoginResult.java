package ru.urvanov.virtualpets.server.controller.game.domain;

public record LoginResult(boolean success, String message,
        Integer userId, String login, String name) {
};
