package ru.urvanov.virtualpets.server.api.domain;

public record LoginResult(boolean success, String message, Integer userId, String unid) {
};
