package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

public class LevelInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8982147622428772679L;
    private Integer experience;
    private Integer maxExperience;
    private Integer minExperience;
    private Integer level;
    public Integer getExperience() {
        return experience;
    }
    public void setExperience(Integer experience) {
        this.experience = experience;
    }
    public Integer getMaxExperience() {
        return maxExperience;
    }
    public void setMaxExperience(Integer maxExperience) {
        this.maxExperience = maxExperience;
    }
    public Integer getMinExperience() {
        return minExperience;
    }
    public void setMinExperience(Integer minExperience) {
        this.minExperience = minExperience;
    }
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    
    
}
