package ru.urvanov.virtualpets.server.dao.domain;

public class SelectedPet {
    
    
    private Integer id;
    private String name;
    
    public SelectedPet(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
    
    public SelectedPet(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
    }
    
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }

}
