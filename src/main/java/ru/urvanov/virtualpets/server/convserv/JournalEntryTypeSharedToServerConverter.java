package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.server.dao.domain.JournalEntryType;

public class JournalEntryTypeSharedToServerConverter implements Converter<ru.urvanov.virtualpets.shared.domain.JournalEntryType, ru.urvanov.virtualpets.server.dao.domain.JournalEntryType>{

    @Override
    public JournalEntryType convert(
            ru.urvanov.virtualpets.shared.domain.JournalEntryType source) {
        return ru.urvanov.virtualpets.server.dao.domain.JournalEntryType.values()[source.ordinal()];
    }

}
