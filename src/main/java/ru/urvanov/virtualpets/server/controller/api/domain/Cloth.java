package ru.urvanov.virtualpets.server.controller.api.domain;

import ru.urvanov.virtualpets.server.dao.domain.ClothType;

public record Cloth(String id, ClothType clothType, int wardrobeOrder) {}
