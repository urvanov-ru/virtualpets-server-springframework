package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class PetJournalEntry implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5790281724199569165L;
    private Date createdAt;
    private JournalEntryType code;
    
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public JournalEntryType getCode() {
        return code;
    }
    public void setCode(JournalEntryType code) {
        this.code = code;
    }

}
