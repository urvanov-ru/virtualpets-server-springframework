package ru.urvanov.virtualpets.server.service.domain;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class HiddenObjectsGameStatus {
    private Integer gameId;
    private boolean hiddenObjectsGameStarted;
    private boolean hiddenObjectsGameOver;
    private boolean hiddenObjectsGameType;
    public Integer getGameId() {
        return gameId;
    }
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }
    public boolean isHiddenObjectsGameStarted() {
        return hiddenObjectsGameStarted;
    }
    public void setHiddenObjectsGameStarted(
            boolean hiddenObjectsGameStarted) {
        this.hiddenObjectsGameStarted = hiddenObjectsGameStarted;
    }
    public boolean isHiddenObjectsGameOver() {
        return hiddenObjectsGameOver;
    }
    public void setHiddenObjectsGameOver(boolean hiddenObjectsGameOver) {
        this.hiddenObjectsGameOver = hiddenObjectsGameOver;
    }
    public boolean isHiddenObjectsGameType() {
        return hiddenObjectsGameType;
    }
    public void setHiddenObjectsGameType(boolean hiddenObjectsGameType) {
        this.hiddenObjectsGameType = hiddenObjectsGameType;
    }
    
}
