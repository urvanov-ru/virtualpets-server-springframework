package ru.urvanov.virtualpets.server.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.urvanov.virtualpets.server.dao.domain.Book;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("bookDao")
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public Book findById(Integer id) {
        return em.find(Book.class, id);
    }

}
