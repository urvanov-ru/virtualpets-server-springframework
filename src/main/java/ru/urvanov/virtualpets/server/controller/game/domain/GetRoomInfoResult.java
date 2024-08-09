package ru.urvanov.virtualpets.server.controller.game.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.AchievementId;

public class GetRoomInfoResult implements Serializable {

    private static final long serialVersionUID = -9110703336298015250L;

    private int mood;
    private int satiety;
    private int education;
    private int drink;
    private String hatId;
    private String clothId;
    private String bowId;
    private Integer refrigeratorId;
    private Integer refrigeratorX;
    private Integer refrigeratorY;
    private boolean[] boxesNewbie;
    private boolean journalOnFloor;
    private Integer bookcaseId;
    private Integer bookcaseX;
    private Integer bookcaseY;
    private Integer machineWithDrinksId;
    private Integer machineWithDrinksX;
    private Integer machineWithDrinksY;
    private long newJournalEntriesCount;
    private boolean haveJournal;
    private boolean haveRucksack;
    private boolean haveHammer;
    private boolean haveIndicators;
    private boolean haveToTownArrow;
    private LevelInfo levelInfo;
    private List<AchievementId> achievements = new ArrayList<>();
    public int getMood() {
        return mood;
    }
    public void setMood(int mood) {
        this.mood = mood;
    }
    public int getSatiety() {
        return satiety;
    }
    public void setSatiety(int satiety) {
        this.satiety = satiety;
    }
    public int getEducation() {
        return education;
    }
    public void setEducation(int education) {
        this.education = education;
    }
    public int getDrink() {
        return drink;
    }
    public void setDrink(int drink) {
        this.drink = drink;
    }
    public String getHatId() {
        return hatId;
    }
    public void setHatId(String hatId) {
        this.hatId = hatId;
    }
    public String getClothId() {
        return clothId;
    }
    public void setClothId(String clothId) {
        this.clothId = clothId;
    }
    public String getBowId() {
        return bowId;
    }
    public void setBowId(String bowId) {
        this.bowId = bowId;
    }
    public Integer getRefrigeratorId() {
        return refrigeratorId;
    }
    public void setRefrigeratorId(Integer refrigeratorId) {
        this.refrigeratorId = refrigeratorId;
    }
    public Integer getRefrigeratorX() {
        return refrigeratorX;
    }
    public void setRefrigeratorX(Integer refrigeratorX) {
        this.refrigeratorX = refrigeratorX;
    }
    public Integer getRefrigeratorY() {
        return refrigeratorY;
    }
    public void setRefrigeratorY(Integer refrigeratorY) {
        this.refrigeratorY = refrigeratorY;
    }
    public boolean[] getBoxesNewbie() {
        return boxesNewbie;
    }
    public void setBoxesNewbie(boolean[] boxesNewbie) {
        this.boxesNewbie = boxesNewbie;
    }
    public boolean isJournalOnFloor() {
        return journalOnFloor;
    }
    public void setJournalOnFloor(boolean journalOnFloor) {
        this.journalOnFloor = journalOnFloor;
    }
    public Integer getBookcaseId() {
        return bookcaseId;
    }
    public void setBookcaseId(Integer bookcaseId) {
        this.bookcaseId = bookcaseId;
    }
    public Integer getBookcaseX() {
        return bookcaseX;
    }
    public void setBookcaseX(Integer bookcaseX) {
        this.bookcaseX = bookcaseX;
    }
    public Integer getBookcaseY() {
        return bookcaseY;
    }
    public void setBookcaseY(Integer bookcaseY) {
        this.bookcaseY = bookcaseY;
    }
    public Integer getMachineWithDrinksId() {
        return machineWithDrinksId;
    }
    public void setMachineWithDrinksId(Integer machineWithDrinksId) {
        this.machineWithDrinksId = machineWithDrinksId;
    }
    public Integer getMachineWithDrinksX() {
        return machineWithDrinksX;
    }
    public void setMachineWithDrinksX(Integer machineWithDrinksX) {
        this.machineWithDrinksX = machineWithDrinksX;
    }
    public Integer getMachineWithDrinksY() {
        return machineWithDrinksY;
    }
    public void setMachineWithDrinksY(Integer machineWithDrinksY) {
        this.machineWithDrinksY = machineWithDrinksY;
    }
    public long getNewJournalEntriesCount() {
        return newJournalEntriesCount;
    }
    public void setNewJournalEntriesCount(long newJournalEntriesCount) {
        this.newJournalEntriesCount = newJournalEntriesCount;
    }
    public boolean isHaveJournal() {
        return haveJournal;
    }
    public void setHaveJournal(boolean haveJournal) {
        this.haveJournal = haveJournal;
    }
    public boolean isHaveRucksack() {
        return haveRucksack;
    }
    public void setHaveRucksack(boolean haveRucksack) {
        this.haveRucksack = haveRucksack;
    }
    public boolean isHaveHammer() {
        return haveHammer;
    }
    public void setHaveHammer(boolean haveHammer) {
        this.haveHammer = haveHammer;
    }
    public boolean isHaveIndicators() {
        return haveIndicators;
    }
    public void setHaveIndicators(boolean haveIndicators) {
        this.haveIndicators = haveIndicators;
    }
    public boolean isHaveToTownArrow() {
        return haveToTownArrow;
    }
    public void setHaveToTownArrow(boolean haveToTownArrow) {
        this.haveToTownArrow = haveToTownArrow;
    }
    public LevelInfo getLevelInfo() {
        return levelInfo;
    }
    public void setLevelInfo(LevelInfo levelInfo) {
        this.levelInfo = levelInfo;
    }
    public List<AchievementId> getAchievements() {
        return achievements;
    }
    public void setAchievements(List<AchievementId> achievements) {
        this.achievements = achievements;
    }

    
    
}
