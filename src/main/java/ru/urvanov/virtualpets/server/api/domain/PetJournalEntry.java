package ru.urvanov.virtualpets.server.api.domain;

import java.time.OffsetDateTime;

import ru.urvanov.virtualpets.server.dao.domain.JournalEntryId;

public record PetJournalEntry(OffsetDateTime createdAt,
        JournalEntryId journalEntryId) {
};
