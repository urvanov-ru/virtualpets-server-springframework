package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Optional;

import ru.urvanov.virtualpets.server.dao.domain.Bookcase;

public interface BookcaseDao {

    Optional<Bookcase> findById(Integer id);

    Optional<Bookcase> findFullById(Integer id);

    List<Bookcase> findAllFull();
}
