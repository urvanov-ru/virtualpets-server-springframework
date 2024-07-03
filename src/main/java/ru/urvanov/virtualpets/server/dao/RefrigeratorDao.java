package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Optional;

import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;

public interface RefrigeratorDao {
    Optional<Refrigerator> findById(Integer id);

    Refrigerator getReference(Integer id);

    Optional<Refrigerator> findFullById(Integer id);

    List<Refrigerator> findAllFull();
}
