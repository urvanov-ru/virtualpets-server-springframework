package ru.urvanov.virtualpets.server.controller.site.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record StatisticsParams (
    @NotNull
    @Min(1)
    @Max(1000)
    Integer maxRecordsCount,

    @NotNull
    StatisticsType type) {
    
    public enum StatisticsType {
        LAST_REGISTERED_USERS, LAST_CREATED_PETS
    }
    
}
