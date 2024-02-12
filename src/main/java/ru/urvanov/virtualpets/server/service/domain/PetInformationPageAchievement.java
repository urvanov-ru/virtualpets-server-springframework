package ru.urvanov.virtualpets.server.service.domain;

public class PetInformationPageAchievement {
    private String code;
    private boolean unlocked;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public boolean isUnlocked() {
        return unlocked;
    }
    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
    
}
