package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

public class GetTownInfoResult implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4492068510344373230L;
    
    private long newJournalEntriesCount;
    private LevelInfo levelInfo;
    private AchievementCode[] achievements;
    
    
    public long getNewJournalEntriesCount() {
        return newJournalEntriesCount;
    }
    public void setNewJournalEntriesCount(long newJournalEntriesCount) {
        this.newJournalEntriesCount = newJournalEntriesCount;
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
