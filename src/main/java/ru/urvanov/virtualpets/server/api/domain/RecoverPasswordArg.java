package ru.urvanov.virtualpets.server.api.domain;

public record RecoverPasswordArg(String host, String login, String email,
        String version) {
};
