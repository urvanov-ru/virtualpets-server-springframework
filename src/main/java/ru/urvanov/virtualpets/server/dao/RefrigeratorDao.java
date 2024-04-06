package ru.urvanov.virtualpets.server.dao;

import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;

public interface RefrigeratorDao {
    Refrigerator findById(Integer id);
    Refrigerator getReference(Integer id);
    Refrigerator findFullById(Integer id);
}
