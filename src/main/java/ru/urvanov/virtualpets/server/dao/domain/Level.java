package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "level")
public class Level implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1477585564717835763L;

    @Id
    @Column(name = "id")
    private Integer id;

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
    public String toString() {
        return "LevelLeague level=" + id + ", experience=" + experience + ". ";
    }

}
