package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Запись из справочника записей в дневник питомца.
 */
@Entity
@Table(name = "journal_entry")
public class JournalEntry implements Serializable {

    private static final long serialVersionUID = 5753060754198528542L;

    /**
     * Первичный ключ записи в справочник записей в дневник питомца.
     * Новые записи добавляются только скриптами liquibase, поэтому первичный
     * ключ не генерируется ни в БД, ни в Java-коде.
     */
    @Id
    private Integer id;

    /**
     * Натуральный ключ. Код записи из справочника записей в дневник питомца.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "code")
    private JournalEntryType code;

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

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JournalEntry other = (JournalEntry) obj;
        return code == other.code;
    }
    
    
}
