package ru.urvanov.virtualpets.server.dao.domain;

public class HiddenObjectsPlayer {
    private Integer userId;
    private Integer petId;
    private String userName;
    private String petName;
    private int score = 0;
    private HiddenObjectsReward reward;
    private String hatId;
    private String clothId;
    private String bowId;
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public HiddenObjectsReward getReward() {
        return reward;
    }

    public void setReward(HiddenObjectsReward reward) {
        this.reward = reward;
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
    

}
