package ru.urvanov.virtualpets.server.api.domain;

import java.util.List;

public record GetPetClothsResult(String hatId,
        String clothId, String bowId, List<Cloth> cloths) {
};
