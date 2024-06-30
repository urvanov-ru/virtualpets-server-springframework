package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.server.api.domain.JournalEntryType;

public class JournalEntryTypeServerToSharedConverter implements Converter<ru.urvanov.virtualpets.server.dao.domain.JournalEntryId, ru.urvanov.virtualpets.server.api.domain.JournalEntryType>{

    @Override
    public JournalEntryType convert(
            ru.urvanov.virtualpets.server.dao.domain.JournalEntryId source) {
        return ru.urvanov.virtualpets.server.api.domain.JournalEntryType.values()[source.ordinal()];
    }
    

}
