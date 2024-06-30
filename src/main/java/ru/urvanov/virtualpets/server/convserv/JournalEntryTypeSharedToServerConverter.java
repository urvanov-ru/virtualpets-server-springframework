package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.server.dao.domain.JournalEntryId;

public class JournalEntryTypeSharedToServerConverter implements Converter<ru.urvanov.virtualpets.server.api.domain.JournalEntryType, ru.urvanov.virtualpets.server.dao.domain.JournalEntryId>{

    @Override
    public JournalEntryId convert(
            ru.urvanov.virtualpets.server.api.domain.JournalEntryType source) {
        return ru.urvanov.virtualpets.server.dao.domain.JournalEntryId.values()[source.ordinal()];
    }

}
