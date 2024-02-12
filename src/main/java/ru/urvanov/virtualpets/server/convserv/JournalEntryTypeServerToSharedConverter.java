package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.shared.domain.JournalEntryType;

public class JournalEntryTypeServerToSharedConverter implements Converter<ru.urvanov.virtualpets.server.dao.domain.JournalEntryType, ru.urvanov.virtualpets.shared.domain.JournalEntryType>{

    @Override
    public JournalEntryType convert(
            ru.urvanov.virtualpets.server.dao.domain.JournalEntryType source) {
        return ru.urvanov.virtualpets.shared.domain.JournalEntryType.values()[source.ordinal()];
    }
    

}
