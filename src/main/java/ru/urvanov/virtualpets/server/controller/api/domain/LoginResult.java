package ru.urvanov.virtualpets.server.controller.api.domain;

public record LoginResult(boolean success, String message,
        Integer userId, String login, String name) {
};
