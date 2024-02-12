package ru.urvanov.virtualpets.server.service.domain;

import java.util.List;

public class PetDetails {
    private Integer id;
    private String name;
    private Integer level;
    private Integer experience;
    private List<PetInformationPageAchievement> achievements;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    public Integer getExperience() {
        return experience;
    }
    public void setExperience(Integer experience) {
        this.experience = experience;
    }
    public List<PetInformationPageAchievement> getAchievements() {
        return achievements;
    }
    public void setAchievements(List<PetInformationPageAchievement> achievements) {
        this.achievements = achievements;
    }
    
}
