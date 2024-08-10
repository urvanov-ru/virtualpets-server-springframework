package ru.urvanov.virtualpets.server.controller.api.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HiddenObjectsGame implements Serializable {
    private static final long serialVersionUID = -1019269544490394403L;

    public static final int MAX_PLAYERS_COUNT = 4;

    private Map<Integer, HiddenObjectsPlayer> players = new HashMap<Integer, HiddenObjectsPlayer>();
    private Integer[] objects;
    private HiddenObjectsCollected[] collectedObjects;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private HiddenObjectsReward reward;
    private int secondsLeft;

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

    public Integer[] getObjects() {
        return objects;
    }

    public void setObjects(Integer[] objects) {
        this.objects = objects;
    }

    public HiddenObjectsCollected[] getCollectedObjects() {
        return collectedObjects;
    }

    public void setCollectedObjects(
            HiddenObjectsCollected[] collectedObjects) {
        this.collectedObjects = collectedObjects;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public HiddenObjectsReward getReward() {
        return reward;
    }

    public void setReward(HiddenObjectsReward reward) {
        this.reward = reward;
    }

    public int getSecondsLeft() {
        return secondsLeft;
    }

    public void setSecondsLeft(int secondsLeft) {
        this.secondsLeft = secondsLeft;
    }

    @Override
    public String toString() {
        return "HiddenObjectsGame [gameStarted=" + gameStarted
                + ", gameOver=" + gameOver + ", secondsLeft="
                + secondsLeft + "]";
    }

}
