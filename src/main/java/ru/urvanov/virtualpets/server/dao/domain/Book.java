package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "book")
public class Book implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1086408475266576270L;

    public static final int MAX_ID = 18;
    
    @Id
    private Integer id;

    @Version
    @Column(name = "version")
    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Book id=" + id;
    }
}
