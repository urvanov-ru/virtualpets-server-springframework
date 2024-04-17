package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ru.urvanov.virtualpets.server.dao.domain.Book;
import ru.urvanov.virtualpets.server.dao.domain.Book_;

@Repository("bookDao")
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public Book findById(String id) {
        return em.find(Book.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAllOrderByBookcaseLevelAndBookcaseOrder() {
        CriteriaBuilder cb = em.getCriteriaBuilder(); // (1)
        CriteriaQuery<Book> criteriaQuery
                = cb.createQuery(Book.class); // (2)
        Root<Book> rootBook = criteriaQuery.from(Book.class); // (3)
        criteriaQuery.select(rootBook); // (4)
        criteriaQuery.orderBy(
                cb.asc(rootBook.get(Book_.bookcaseLevel)),
                cb.asc(rootBook.get(Book_.bookcaseOrder))); // (5)
        TypedQuery<Book> query = em.createQuery(criteriaQuery); // (6)
        return query.getResultList(); // (7)
    }

    @Override
    public Book getReference(String id) {
        return em.getReference(Book.class, id);
    }

}
