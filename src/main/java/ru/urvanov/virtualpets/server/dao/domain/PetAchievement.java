package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name="pet_achievement")
public class PetAchievement implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2006307559583333526L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pet_achievement_seq")
    @SequenceGenerator(name="pet_achievement_seq",
        sequenceName="pet_achievement_id_seq", allocationSize=1)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name="achievement_id")
    private Achievement achievement;
    
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    
    @Column(name = "was_shown")
    private Boolean wasShown = false;
    
    @Version
    private Integer version;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
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

    public Boolean getWasShown() {
        return wasShown;
    }

    public void setWasShown(Boolean wasShown) {
        this.wasShown = wasShown;
    }

    @Override
    public String toString() {
        return "PetAchievement id="+id+", pet.id="+pet == null ? null : pet.getId() + ". ";
    }
}
