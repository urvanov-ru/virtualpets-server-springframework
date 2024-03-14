package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Запись справочника книг.
 */
@Entity
@Table(name = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1086408475266576270L;
    
    public static final String FIRST_BOOOK_ID = "DESTINY";

    /**
     * Первичный ключ книги. Новые книги добавляются скриптами liquibase,
     * первичный ключ не генерируется ни в БД, ни в Java-коде.
     */
    @Id
    private String id;
    
    @Column(name = "bookcase_id")
    private int bookcaseLevel;
    
    @Column(name = "bookcase_order")
    private int bookcaseOrder;
    
    @Column(name = "hidden_objects_game_drop_rate")
    private double hiddenObjectsGameDropRate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBookcaseLevel() {
        return bookcaseLevel;
    }

    public void setBookcaseLevel(int bookcaseLevel) {
        this.bookcaseLevel = bookcaseLevel;
    }

    public int getBookcaseOrder() {
        return bookcaseOrder;
    }

    public void setBookcaseOrder(int bookcaseOrder) {
        this.bookcaseOrder = bookcaseOrder;
    }

    public double getHiddenObjectsGameDropRate() {
        return hiddenObjectsGameDropRate;
    }

    public void setHiddenObjectsGameDropRate(double hiddenObjectsGameDropRate) {
        this.hiddenObjectsGameDropRate = hiddenObjectsGameDropRate;
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
        Book other = (Book) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", bookcaseLevel=" + bookcaseLevel
                + ", bookcaseOrder=" + bookcaseOrder
                + ", hiddenObjectsGameDropRate=" + hiddenObjectsGameDropRate
                + "]";
    }

    
}
