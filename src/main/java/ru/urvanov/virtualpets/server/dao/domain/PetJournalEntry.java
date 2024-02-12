package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name="pet_journal_entry")
public class PetJournalEntry implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2334211168307341971L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pet_journal_entry_seq")
    @SequenceGenerator(name="pet_journal_entry_seq",
        sequenceName="pet_journal_entry_id_seq", allocationSize=1)
    @Column(name="id")
    private Integer id;
    
    @Column(name = "created_at")
    private Date createdAt;
    
    @ManyToOne
    @JoinColumn(name="journal_entry_id")
    private JournalEntry journalEntry;
    
    @ManyToOne
    @JoinColumn(name="pet_id")
    private Pet pet;
    

    @Column
    private Boolean readed = false;
    
    @Version
    @Column(name="version")
    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public JournalEntry getJournalEntry() {
        return journalEntry;
    }

    public void setJournalEntry(JournalEntry journalEntry) {
        this.journalEntry = journalEntry;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getReaded() {
        return readed;
    }

    public void setReaded(Boolean readed) {
        this.readed = readed;
    }
    
    
}
