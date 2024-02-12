package ru.urvanov.virtualpets.shared.service;

import ru.urvanov.virtualpets.shared.domain.GetPetJournalEntriesResult;

public interface JournalEntryService {
    GetPetJournalEntriesResult getPetJournalEntries(int count);
}
