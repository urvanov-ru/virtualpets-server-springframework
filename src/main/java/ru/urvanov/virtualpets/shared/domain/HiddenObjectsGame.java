/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fedya
 *
 */
public class HiddenObjectsGame implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -1019269544490394403L;
    
    public static final int MAX_PLAYERS_COUNT = 4;
    
    private Map<Integer, HiddenObjectsPlayer> players = new HashMap<Integer, HiddenObjectsPlayer>();
    private Integer[] objects;
    private HiddenObjectsCollected[] collectedObjects;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private HiddenObjectsReward reward;
    private int secondsLeft;
    /**
     * @return the players
     */
    public HiddenObjectsPlayer[] getPlayers() {
        HiddenObjectsPlayer[] result = new HiddenObjectsPlayer[MAX_PLAYERS_COUNT];
        int n = 0;
        Collection<HiddenObjectsPlayer> collection = players.values();
        for (HiddenObjectsPlayer p : collection) {
            result[n] = p;
            n++;
        }
        return result;
    }
    
    public void clearPlayers() {
        players.clear();
    }
    
    public void addPlayer(HiddenObjectsPlayer player) {
        players.put(player.getUserId(), player);
    }
    
    public HiddenObjectsPlayer getPlayer(Integer userId) {
        return players.get(userId);
    }
    
    /**
     * @return the objects
     */
    public Integer[] getObjects() {
        return objects;
    }
    /**
     * @param objects the objects to set
     */
    public void setObjects(Integer[] objects) {
        this.objects = objects;
    }
    /**
     * @return the collectedObjects
     */
    public HiddenObjectsCollected[] getCollectedObjects() {
        return collectedObjects;
    }
    /**
     * @param collectedObjects the collectedObjects to set
     */
    public void setCollectedObjects(HiddenObjectsCollected[] collectedObjects) {
        this.collectedObjects = collectedObjects;
    }
    /**
     * @return the gameStarted
     */
    public boolean isGameStarted() {
        return gameStarted;
    }
    /**
     * @param gameStarted the gameStarted to set
     */
    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    /**
     * @return the gameOver
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * @param gameOver the gameOver to set
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
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
     * @return the secondsLeft
     */
    public int getSecondsLeft() {
        return secondsLeft;
    }

    /**
     * @param secondsLeft the secondsLeft to set
     */
    public void setSecondsLeft(int secondsLeft) {
        this.secondsLeft = secondsLeft;
    }

    
}
