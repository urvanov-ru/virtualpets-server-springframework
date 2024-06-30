package ru.urvanov.virtualpets.server.service.domain;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class SelectedPet {
    private Integer petId;
    private List<Integer> petIds;

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public List<Integer> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Integer> petIds) {
        this.petIds = petIds;
    }

}
