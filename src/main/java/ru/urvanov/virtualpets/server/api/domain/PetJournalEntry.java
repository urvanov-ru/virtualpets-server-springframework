package ru.urvanov.virtualpets.server.api.domain;

import java.time.OffsetDateTime;

import ru.urvanov.virtualpets.server.dao.domain.JournalEntryId;

public record PetJournalEntry(OffsetDateTime createdAt,
        JournalEntryId id) implements Comparable<PetJournalEntry> {

    @Override
    public int compareTo(PetJournalEntry o) {
        return this.createdAt.compareTo(o.createdAt);
    }


};
