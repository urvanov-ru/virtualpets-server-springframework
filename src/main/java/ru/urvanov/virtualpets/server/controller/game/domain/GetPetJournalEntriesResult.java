package ru.urvanov.virtualpets.server.controller.game.domain;

import java.util.List;

public record GetPetJournalEntriesResult(List<PetJournalEntry> entries) {
};
