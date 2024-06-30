package ru.urvanov.virtualpets.server.api.domain;

public record RegisterArgument(String host, String login,
        String password, String email, String version) {
};
