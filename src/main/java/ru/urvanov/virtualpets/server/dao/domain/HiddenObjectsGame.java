/**
 * 
 */
package ru.urvanov.virtualpets.server.dao.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fedya
 *
 */
public class HiddenObjectsGame {
    public static final int COUNT_DISPLAYABLE_OBJECTS = 4;
    public static final int MAX_PLAYERS_COUNT = 4;
    
    /**
     * Ключ - идентификатор пользователя.
     */
    private Map<Integer, HiddenObjectsPlayer> players = new HashMap<Integer, HiddenObjectsPlayer>();
    private List<Integer> objects = new ArrayList<Integer>();
    private List<HiddenObjectsCollected> collectedObjects = new ArrayList<HiddenObjectsCollected>();
    
    private boolean started = false;
    private boolean gameOver = false;
    private Calendar startTime;
    
    public Integer[] getObjectsForSearch() {
        Integer[] result = new Integer[COUNT_DISPLAYABLE_OBJECTS];
        int n = 0;
        for (Integer obj : objects) {
            result[n] = obj;
            n++;
            if (n == COUNT_DISPLAYABLE_OBJECTS) {
                break;
            }
        }
        return result;
    }
    
    public void removeObjectForSearch(Integer objectId) {
        objects.remove(objectId);
    }
    
    public HiddenObjectsPlayer[] getDisplayablePlayers() {
        HiddenObjectsPlayer[] result = new HiddenObjectsPlayer[MAX_PLAYERS_COUNT];
        int n = 0;
        for (HiddenObjectsPlayer player : players.values()) {
            result[n] = player;
            n++;
            if (n == MAX_PLAYERS_COUNT) {
                break;
            }
        }
        return result;
    }
    
    public void setObjects(List<Integer> objects) {
        this.objects = objects;
    }
    
    /**
     * @param player Информация и игроке.
     */
    public void addPlayer(HiddenObjectsPlayer player) {
        players.put(player.getUserId(), player);
    }
    
    /**
     * @param id Идентификатор пользователя.
     */
    public void removePlayer(Integer id) {
        players.remove(id);
    }

    /**
     * @return the started
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * @param started the started to set
     */
    public void setStarted(boolean started) {
        this.started = started;
    }
    
    public int getPetsCount() {
        return players.size();
    }
    
    public void addCollectedObject(Integer objectId, HiddenObjectsPlayer player) {
        HiddenObjectsCollected hiddenObjectsCollected = new HiddenObjectsCollected();
        hiddenObjectsCollected.setObjectId(objectId);
        hiddenObjectsCollected.setPlayer(player);
        collectedObjects.add(hiddenObjectsCollected);
    }
    
    public HiddenObjectsCollected[] getCollectedObjects() {
        HiddenObjectsCollected[] result = new HiddenObjectsCollected[collectedObjects.size()];
        int n =0;
        for (HiddenObjectsCollected hoc : collectedObjects) {
            result[n] = hoc;
            n++;
        }
        return result;
    }
    
    public HiddenObjectsPlayer getPlayer(Integer id) {
        return players.get(id);
    }

    public int getObjectsForSearchCount() {
        return objects.size();
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
     * @return the startTime
     */
    public Calendar getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

}
