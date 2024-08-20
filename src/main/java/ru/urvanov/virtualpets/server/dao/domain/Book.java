package ru.urvanov.virtualpets.server.dao.domain;

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
public class Book {
    
    public static final String FIRST_BOOOK_ID = "DESTINY";

    /**
     * Первичный ключ книги. Новые книги добавляются скриптами liquibase,
     * первичный ключ не генерируется ни в БД, ни в Java-коде.
     */
    @Id
    private String id;
    
    @Column(name = "bookcase_id")
    private int bookcaseLevel;
    
    private int bookcaseOrder;
    
    private float hiddenObjectsGameDropRate;

    /**
     * Конструктор по умолчанию требуется JPA для создания объекта.
     */
    public Book() {
        super();
    }

    /**
     * Конструктор для создания экземпляров в самом приложении
     * виртуальных питомцев, например в тестах.
     * @param id {@link #id}
     * @param bookcaseLevel {@link #bookcaseLevel}
     * @param bookcaseOrder {@link #bookcaseOrder}
     * @param hiddenObjectsGameDropRate {@link hiddenObjectsGameDropRate} 
     */
    public Book(String id, int bookcaseLevel, int bookcaseOrder,
            float hiddenObjectsGameDropRate) {
        super();
        this.id = id;
        this.bookcaseLevel = bookcaseLevel;
        this.bookcaseOrder = bookcaseOrder;
        this.hiddenObjectsGameDropRate = hiddenObjectsGameDropRate;
    }

    public String getId() {
        return id;
    }

    public int getBookcaseLevel() {
        return bookcaseLevel;
    }

    public int getBookcaseOrder() {
        return bookcaseOrder;
    }

    public float getHiddenObjectsGameDropRate() {
        return hiddenObjectsGameDropRate;
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
                + ", hiddenObjectsGameDropRate="
                        + hiddenObjectsGameDropRate
                + "]";
    }

    
}
