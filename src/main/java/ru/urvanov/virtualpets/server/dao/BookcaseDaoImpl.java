package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ru.urvanov.virtualpets.server.dao.domain.Bookcase;
import ru.urvanov.virtualpets.server.dao.domain.Bookcase_;

@Repository(value="bookcaseDao")
public class BookcaseDaoImpl implements BookcaseDao {

    private static final Logger log = LoggerFactory.getLogger(BookcaseDaoImpl.class);
    
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public Optional<Bookcase> findFullById(Integer id) {
         CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
         CriteriaQuery<Bookcase> criteriaQuery = criteriaBuilder
                 .createQuery(Bookcase.class);
         Root<Bookcase> root = criteriaQuery.from(Bookcase.class);
         root.fetch(Bookcase_.bookcaseCosts, JoinType.LEFT);
         criteriaQuery.select(root);
         Predicate predicate = criteriaBuilder.equal(
                 root.get(Bookcase_.id), id);
         criteriaQuery.where(predicate);
         TypedQuery<Bookcase> typedQuery = em.createQuery(
                 criteriaQuery);
         List<Bookcase> bookcases = typedQuery.getResultList();
         return DataAccessUtils.optionalResult(bookcases);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bookcase> findById(Integer id) {
        Bookcase bookcase = em.find(Bookcase.class, id);
        return Optional.ofNullable(bookcase);
    }

    @Override
    public List<Bookcase> findAllFull() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Bookcase> criteriaQuery = criteriaBuilder
                .createQuery(Bookcase.class);
        Root<Bookcase> root = criteriaQuery.from(Bookcase.class);
        root.fetch(Bookcase_.bookcaseCosts, JoinType.LEFT);
        criteriaQuery.select(root);
        TypedQuery<Bookcase> typedQuery = em.createQuery(
                criteriaQuery);
        return typedQuery.getResultList();
    }


}
