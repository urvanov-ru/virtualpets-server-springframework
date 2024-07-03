package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Optional;

import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks;

public interface MachineWithDrinksDao {
    
    Optional<MachineWithDrinks> findById(Integer id);

    MachineWithDrinks getReference(Integer id);

    Optional<MachineWithDrinks> findFullById(Integer id);

    List<MachineWithDrinks> findAllFull();
}
