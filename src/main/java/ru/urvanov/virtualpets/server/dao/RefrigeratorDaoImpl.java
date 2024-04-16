package ru.urvanov.virtualpets.server.dao;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId;
import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;
import ru.urvanov.virtualpets.server.dao.domain.RefrigeratorCost;
import ru.urvanov.virtualpets.server.dao.domain.Refrigerator_;

@Repository(value = "refrigeratorDao")
@Transactional
public class RefrigeratorDaoImpl implements RefrigeratorDao {

    private static final Logger log = LoggerFactory.getLogger(RefrigeratorDaoImpl.class);
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public Refrigerator findById(Integer id) {
        return em.find(Refrigerator.class, id);
    }

    @Override
    public Refrigerator findFullById(Integer id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Refrigerator> criteriaQuery = criteriaBuilder
                .createQuery(Refrigerator.class);
        Root<Refrigerator> root = criteriaQuery.from(Refrigerator.class);
        Predicate predicate = criteriaBuilder.equal(
                root.get(Refrigerator_.id),
                id);
        criteriaQuery.where(predicate);
        TypedQuery<Refrigerator> query = em.createQuery(criteriaQuery);
        Refrigerator refrigerator = query.getSingleResult();
        Map<BuildingMaterialId, RefrigeratorCost> refrigeratorCost
                = refrigerator.getRefrigeratorCost();
        log.debug("refrigeratorCost size = %n", refrigeratorCost.size());
        return refrigerator;
    }

    @Override
    public Refrigerator getReference(Integer id) {
        return em.getReference(Refrigerator.class, id);
    }

}
