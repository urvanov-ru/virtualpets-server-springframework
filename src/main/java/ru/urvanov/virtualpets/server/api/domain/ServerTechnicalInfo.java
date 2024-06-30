package ru.urvanov.virtualpets.server.api.domain;

import java.util.Map;

public record ServerTechnicalInfo(Map<String, String> info) {
};
