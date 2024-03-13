package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * Запись о достижении питомца.
 */
@Entity
@Table(name="pet_achievement")
public class PetAchievement implements Serializable {

    private static final long serialVersionUID = -2006307559583333526L;

    /**
     * Первичный ключ записи. Генерируемый.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pet_achievement_seq")
    @SequenceGenerator(name="pet_achievement_seq",
        sequenceName="pet_achievement_id_seq", allocationSize=1)
    private Integer id;
    
    @Column(name = "achievement_id")
    @Enumerated(EnumType.STRING)
    private AchievementCode achievement;
    
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    
    /**
     * Отображалось ли игроку сообщение о получении достижения.
     */
    @Column(name = "was_shown")
    private Boolean wasShown = false;
    
    /**
     * Для оптимистичной блокировки.
     */
    @Version
    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AchievementCode getAchievement() {
        return achievement;
    }

    public void setAchievement(AchievementCode achievement) {
        this.achievement = achievement;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Boolean getWasShown() {
        return wasShown;
    }

    public void setWasShown(Boolean wasShown) {
        this.wasShown = wasShown;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getVersion() {
        return version;
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
        PetAchievement other = (PetAchievement) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "PetAchievement [id=" + id + ", achievement=" + achievement
                + ", pet=" + pet + ", wasShown=" + wasShown + ", version="
                + version + "]";
    }
    
    
}
