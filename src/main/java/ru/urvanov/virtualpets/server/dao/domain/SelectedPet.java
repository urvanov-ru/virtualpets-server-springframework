package ru.urvanov.virtualpets.server.dao.domain;

import java.util.Objects;

/**
 * Выбранный питомец. Для хранения в сессии информации о выбранном питомце.
 */
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

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SelectedPet other = (SelectedPet) obj;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "SelectedPet [id=" + id + ", name=" + name + "]";
    }

    
}
