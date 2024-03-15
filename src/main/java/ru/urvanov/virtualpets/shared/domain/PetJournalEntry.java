package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class PetJournalEntry implements Serializable {

    private static final long serialVersionUID = 5790281724199569165L;
    private OffsetDateTime createdAt;
    private JournalEntryType code;
    
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public JournalEntryType getCode() {
        return code;
    }
    public void setCode(JournalEntryType code) {
        this.code = code;
    }

}
