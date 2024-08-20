package ru.urvanov.virtualpets.server.dao.domain;

import java.time.OffsetDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * Запись о добавленной записи в журнал питомца.
 */
@Entity
@Table(name="pet_journal_entry")
public class PetJournalEntry {

    /**
     * Первичный ключ. Генерируемый.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pet_journal_entry_seq")
    @SequenceGenerator(name="pet_journal_entry_seq",
        sequenceName="pet_journal_entry_id_seq", allocationSize=1)
    private Integer id;
    
    private OffsetDateTime createdAt;
    
    @Column(name = "journal_entry_id")
    @Enumerated(EnumType.STRING)
    private JournalEntryId journalEntry;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Pet pet;
    

    private boolean readed = false;
    
    /**
     * Для оптимистичной блокировки.
     */
    @Version
    private int version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public JournalEntryId getJournalEntry() {
        return journalEntry;
    }

    public void setJournalEntry(JournalEntryId journalEntry) {
        this.journalEntry = journalEntry;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PetJournalEntry other = (PetJournalEntry) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "PetJournalEntry [id=" + id + ", createdAt=" + createdAt
                + ", journalEntry=" + journalEntry + ", pet=" + pet
                + ", readed=" + readed + ", version=" + version + "]";
    }

}
