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
@Table(name = "achievement")
public class Achievement implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5719570475809622492L;

    @Id
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "code")
    private AchievementCode code;

    @Version
    private Integer version;

    @Override
    public String toString() {
        return "Achievement id=" + id + ", code=" + code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AchievementCode getCode() {
        return code;
    }

    public void setCode(AchievementCode code) {
        this.code = code;
    }

    public Integer getVersion() {
        return version;
    }

    @Override
    public int hashCode() {
        return id == null ? -1 : id;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Achievement) {
            Achievement otherAchievement = (Achievement) other;
            if (otherAchievement.id != null && this.id != null
                    && this.id.equals(otherAchievement.id))
                return true;
        }
        return false;
    }
}
