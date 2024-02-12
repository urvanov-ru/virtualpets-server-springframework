package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.JournalEntryType;
import ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry;

public interface PetJournalEntryDao {
    List<PetJournalEntry> findLastByPetId(Integer petId, Integer count);

    PetJournalEntry findByPetIdAndJournalEntryCode(Integer petId,
            JournalEntryType code);

    void save(PetJournalEntry petJournalEntry);

}
