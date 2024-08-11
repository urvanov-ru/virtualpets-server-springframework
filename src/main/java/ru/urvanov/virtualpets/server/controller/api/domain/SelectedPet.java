package ru.urvanov.virtualpets.server.controller.api.domain;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/**
 * Хранящийся в сессии первичный ключ выбранного пользователем для 
 * игры питомца.
 */
@Component
@SessionScope
public class SelectedPet {
    private Integer petId;

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }
    
    

}
