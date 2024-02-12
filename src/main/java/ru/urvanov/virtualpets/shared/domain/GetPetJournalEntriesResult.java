package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

public class GetPetJournalEntriesResult implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -456191926816948470L;
    
    PetJournalEntry[] entries;

    public PetJournalEntry[] getEntries() {
        return entries;
    }

    public void setEntries(PetJournalEntry[] entries) {
        this.entries = entries;
    }
    
}
