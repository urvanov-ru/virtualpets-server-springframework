package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.JournalEntryId;
import ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry;

public interface PetJournalEntryDao {
    List<PetJournalEntry> findLastByPetId(Integer petId, Integer count);

    PetJournalEntry findByPetIdAndJournalEntryCode(Integer petId,
            JournalEntryId code);

    void save(PetJournalEntry petJournalEntry);

}
