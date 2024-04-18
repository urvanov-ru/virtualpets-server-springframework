package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks;

public interface MachineWithDrinksDao {
    MachineWithDrinks findById(Integer id);
    MachineWithDrinks getReference(Integer id);
    MachineWithDrinks findFullById(Integer id);
    List<MachineWithDrinks> findAllFull();
}
