package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;

public interface RefrigeratorDao {
    Refrigerator findById(Integer id);
    Refrigerator getReference(Integer id);
    Refrigerator findFullById(Integer id);
    List<Refrigerator> findAllFull();
}
