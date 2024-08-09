package ru.urvanov.virtualpets.server.controller.api.domain;

public record SendChatMessageArg(Integer addresseeId, String message) {
};
