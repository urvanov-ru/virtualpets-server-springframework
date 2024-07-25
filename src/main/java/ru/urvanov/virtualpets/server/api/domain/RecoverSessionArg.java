package ru.urvanov.virtualpets.server.api.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RecoverSessionArg(
        @NotNull @Size(min = 1, max = 50) String unid,
        @NotNull @Size(max = 50) String version) {
};
