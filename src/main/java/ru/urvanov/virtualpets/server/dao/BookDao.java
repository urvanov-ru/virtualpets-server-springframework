package ru.urvanov.virtualpets.server.dao;

import ru.urvanov.virtualpets.server.dao.domain.Book;

public interface BookDao {
    Book findById(Integer id);
}
