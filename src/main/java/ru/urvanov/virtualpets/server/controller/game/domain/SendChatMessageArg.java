package ru.urvanov.virtualpets.server.controller.game.domain;

public record SendChatMessageArg(Integer addresseeId, String message) {
};
