package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Optional;

import ru.urvanov.virtualpets.server.dao.domain.Cloth;

public interface ClothDao {

    Optional<Cloth> findById(String id);

    Cloth getReference(String id);

    Integer getCount();

    List<Cloth> findAll();
}
