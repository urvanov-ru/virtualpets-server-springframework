/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

/**
 * @author fedya
 * 
 */
public class GetRoomInfoResult implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9110703336298015250L;

    private int mood;
    private int satiety;
    private int education;
    private int drink;
    private Integer hatId;
    private Integer clothId;
    private Integer bowId;
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
    private AchievementCode[] achievements;

    /**
     * @return the mood
     */
    public int getMood() {
        return mood;
    }

    /**
     * @param mood
     *            the mood to set
     */
    public void setMood(int mood) {
        this.mood = mood;
    }

    /**
     * @return the satiety
     */
    public int getSatiety() {
        return satiety;
    }

    /**
     * @param satiety
     *            the satiety to set
     */
    public void setSatiety(int satiety) {
        this.satiety = satiety;
    }

    /**
     * @return the education
     */
    public int getEducation() {
        return education;
    }

    /**
     * @param education
     *            the education to set
     */
    public void setEducation(int education) {
        this.education = education;
    }

    /**
     * @return the drink
     */
    public int getDrink() {
        return drink;
    }

    /**
     * @param drink
     *            the drink to set
     */
    public void setDrink(int drink) {
        this.drink = drink;
    }

    /**
     * @return the hatId
     */
    public Integer getHatId() {
        return hatId;
    }

    /**
     * @param hatId
     *            the hatId to set
     */
    public void setHatId(Integer hatId) {
        this.hatId = hatId;
    }

    /**
     * @return the clothId
     */
    public Integer getClothId() {
        return clothId;
    }

    /**
     * @param clothId
     *            the clothId to set
     */
    public void setClothId(Integer clothId) {
        this.clothId = clothId;
    }

    /**
     * @return the bowId
     */
    public Integer getBowId() {
        return bowId;
    }

    /**
     * @param bowId
     *            the bowId to set
     */
    public void setBowId(Integer bowId) {
        this.bowId = bowId;
    }

    /**
     * @return the refrigeratorId
     */
    public Integer getRefrigeratorId() {
        return refrigeratorId;
    }

    /**
     * @param refrigeratorId the refrigeratorId to set
     */
    public void setRefrigeratorId(Integer refrigeratorId) {
        this.refrigeratorId = refrigeratorId;
    }

    /**
     * @return the refrigeratorX
     */
    public Integer getRefrigeratorX() {
        return refrigeratorX;
    }

    /**
     * @param refrigeratorX the refrigeratorX to set
     */
    public void setRefrigeratorX(Integer refrigeratorX) {
        this.refrigeratorX = refrigeratorX;
    }

    /**
     * @return the refrigeratorY
     */
    public Integer getRefrigeratorY() {
        return refrigeratorY;
    }

    /**
     * @param refrigeratorY the refrigeratorY to set
     */
    public void setRefrigeratorY(Integer refrigeratorY) {
        this.refrigeratorY = refrigeratorY;
    }


    /**
     * @return the bookcaseId
     */
    public Integer getBookcaseId() {
        return bookcaseId;
    }

    /**
     * @param bookcaseId the bookcaseId to set
     */
    public void setBookcaseId(Integer bookcaseId) {
        this.bookcaseId = bookcaseId;
    }

    /**
     * @return the bookcaseX
     */
    public Integer getBookcaseX() {
        return bookcaseX;
    }

    /**
     * @param bookcaseX the bookcaseX to set
     */
    public void setBookcaseX(Integer bookcaseX) {
        this.bookcaseX = bookcaseX;
    }

    /**
     * @return the bookcaseY
     */
    public Integer getBookcaseY() {
        return bookcaseY;
    }

    /**
     * @param bookcaseY the bookcaseY to set
     */
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

    /**
     * @return the boxesNewbie
     */
    public boolean[] getBoxesNewbie() {
        return boxesNewbie;
    }

    /**
     * @param boxesNewbie the boxesNewbie to set
     */
    public void setBoxesNewbie(boolean[] boxesNewbie) {
        this.boxesNewbie = boxesNewbie;
    }

    public boolean getJournalOnFloor() {
        return journalOnFloor;
    }

    public void setJournalOnFloor(boolean journalOnFloor) {
        this.journalOnFloor = journalOnFloor;
    }

    public long getNewJournalEntriesCount() {
        return newJournalEntriesCount;
    }

    public void setNewJournalEntriesCount(long newJournalEntriesCount) {
        this.newJournalEntriesCount = newJournalEntriesCount;
    }

    public boolean getHaveJournal() {
        return haveJournal;
    }

    public void setHaveJournal(boolean haveJournal) {
        this.haveJournal = haveJournal;
    }

    public boolean getHaveRucksack() {
        return haveRucksack;
    }

    public void setHaveRucksack(boolean haveRucksack) {
        this.haveRucksack = haveRucksack;
    }

    public boolean getHaveHammer() {
        return haveHammer;
    }

    public void setHaveHammer(boolean haveHammer) {
        this.haveHammer = haveHammer;
    }

    public boolean getHaveIndicators() {
        return haveIndicators;
    }

    public void setHaveIndicators(boolean haveIndicators) {
        this.haveIndicators = haveIndicators;
    }

    public boolean getHaveToTownArrow() {
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

    public AchievementCode[] getAchievements() {
        return achievements;
    }

    public void setAchievements(AchievementCode[] achievements) {
        this.achievements = achievements;
    }




}
