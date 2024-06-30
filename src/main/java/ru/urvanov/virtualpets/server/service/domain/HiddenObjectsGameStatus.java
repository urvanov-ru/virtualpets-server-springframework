package ru.urvanov.virtualpets.server.service.domain;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import ru.urvanov.virtualpets.server.api.domain.HiddenObjectsGameType;

@Component
@SessionScope
public class HiddenObjectsGameStatus {
    private Integer Id;
    private boolean started;
    private boolean over;
    private HiddenObjectsGameType type;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public HiddenObjectsGameType getType() {
        return type;
    }

    public void setType(HiddenObjectsGameType type) {
        this.type = type;
    }
}