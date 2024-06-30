package ru.urvanov.virtualpets.server.api.domain;

public record SendChatMessageArg(Integer addresseeId, String message) {
};
