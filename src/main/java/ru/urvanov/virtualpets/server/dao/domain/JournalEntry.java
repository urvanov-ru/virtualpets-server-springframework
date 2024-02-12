package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "journal_entry")
public class JournalEntry implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5753060754198528542L;

    @Id
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "code")
    private JournalEntryType code;

    @Version
    @Column(name = "version")
    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JournalEntryType getCode() {
        return code;
    }

    public void setCode(JournalEntryType code) {
        this.code = code;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        return id == null ? -1 : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof JournalEntry) {
            JournalEntry other = (JournalEntry) obj;
            if (other.getId() != null && other.getId().equals(id))
                return true;
            else
                return false;
        } else
            return false;
    }
}
