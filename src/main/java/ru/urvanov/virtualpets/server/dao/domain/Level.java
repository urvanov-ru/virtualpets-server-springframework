package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Запись из справочника уровней питомца.
 */
@Entity
@Table(name = "level")
public class Level implements Serializable {

    private static final long serialVersionUID = 1477585564717835763L;

    /**
     * Первичный ключ. Новые записи в справочник уровней питомца
     * добавляются только из скриптов liquibase, поэтому первичный ключ 
     * не генерируется ни в БД, ни в Java-коде.
     */
    @Id
    private Integer id;

    /**
     * Количество опыта, которое необходимо набрать питомцу
     *  для достижения уровня.
     */
    @Column(name = "experience")
    private Integer experience;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
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
        Level other = (Level) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Level [id=" + id + ", experience=" + experience + "]";
    }

    
}
