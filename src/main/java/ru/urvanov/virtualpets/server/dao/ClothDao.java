package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.Cloth;

public interface ClothDao {
    public Cloth findById(String id);
    public Cloth getReference(String id);
    public Integer getCount();
    public List<Cloth> findAll();
}
