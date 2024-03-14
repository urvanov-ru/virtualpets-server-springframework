package ru.urvanov.virtualpets.server.dao;

import ru.urvanov.virtualpets.server.dao.domain.Bookcase;

public interface BookcaseDao {
    Bookcase findById(Integer id);
    Bookcase findFullById(Integer id);
}
