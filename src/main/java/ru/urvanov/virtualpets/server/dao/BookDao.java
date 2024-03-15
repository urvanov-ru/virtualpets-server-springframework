package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.Book;

public interface BookDao {
    Book findById(String id);

    List<Book> findAllOrderByBookcaseLevelAndBookcaseOrder();

    Book getReference(String id);
}
