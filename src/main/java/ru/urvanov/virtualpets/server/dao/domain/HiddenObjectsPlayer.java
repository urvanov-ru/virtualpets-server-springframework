/**
 * 
 */
package ru.urvanov.virtualpets.server.dao.domain;

/**
 * @author fedya
 *
 */
public class HiddenObjectsPlayer {
    private Integer userId;
    private Integer petId;
    private String userName;
    private String petName;
    private int score = 0;
    private HiddenObjectsReward reward;
    private Integer hatId;
    private Integer clothId;
    private Integer bowId;
    
    /**
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    /**
     * @return the petId
     */
    public Integer getPetId() {
        return petId;
    }
    /**
     * @param petId the petId to set
     */
    public void setPetId(Integer petId) {
        this.petId = petId;
    }
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * @return the petName
     */
    public String getPetName() {
        return petName;
    }
    /**
     * @param petName the petName to set
     */
    public void setPetName(String petName) {
        this.petName = petName;
    }
    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }
    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }
    /**
     * @return the reward
     */
    public HiddenObjectsReward getReward() {
        return reward;
    }
    /**
     * @param reward the reward to set
     */
    public void setReward(HiddenObjectsReward reward) {
        this.reward = reward;
    }
    /**
     * @return the hatId
     */
    public Integer getHatId() {
        return hatId;
    }
    /**
     * @param hatId the hatId to set
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
     * @param clothId the clothId to set
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
     * @param bowId the bowId to set
     */
    public void setBowId(Integer bowId) {
        this.bowId = bowId;
    }
    

}
