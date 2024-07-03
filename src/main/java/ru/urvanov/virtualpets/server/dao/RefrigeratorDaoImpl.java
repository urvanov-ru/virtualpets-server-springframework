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
import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;
import ru.urvanov.virtualpets.server.dao.domain.Refrigerator_;

@Repository(value = "refrigeratorDao")
@Transactional
public class RefrigeratorDaoImpl implements RefrigeratorDao {

    private static final Logger log = LoggerFactory.getLogger(RefrigeratorDaoImpl.class);
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Refrigerator> findById(Integer id) {
        Refrigerator refrigerator = em.find(Refrigerator.class, id);
        return Optional.ofNullable(refrigerator);
    }

    @Override
    public Optional<Refrigerator> findFullById(Integer id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Refrigerator> criteriaQuery = criteriaBuilder
                .createQuery(Refrigerator.class);
        Root<Refrigerator> root = criteriaQuery.from(Refrigerator.class);
        root.fetch(Refrigerator_.refrigeratorCost);
        Predicate predicate = criteriaBuilder.equal(
                root.get(Refrigerator_.id),
                id);
        criteriaQuery.where(predicate);
        TypedQuery<Refrigerator> query = em.createQuery(criteriaQuery);
        List<Refrigerator> refrigerators = query.getResultList();
        return DataAccessUtils.optionalResult(refrigerators);
    }

    @Override
    public Refrigerator getReference(Integer id) {
        return em.getReference(Refrigerator.class, id);
    }

    @Override
    public List<Refrigerator> findAllFull() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Refrigerator> criteriaQuery = criteriaBuilder
                .createQuery(Refrigerator.class);
        Root<Refrigerator> root = criteriaQuery.from(Refrigerator.class);
        root.fetch(Refrigerator_.refrigeratorCost, JoinType.LEFT);
        TypedQuery<Refrigerator> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

}
