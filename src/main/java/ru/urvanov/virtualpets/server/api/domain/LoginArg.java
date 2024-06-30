package ru.urvanov.virtualpets.server.api.domain;

public record LoginArg(String host, String login, String password,
        String version) {
};
