package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks;
import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks_;

@Repository(value="machineWithDrinksDao")
public class MachineWithDrinksDaoImpl implements MachineWithDrinksDao {
    
    private static final Logger log = LoggerFactory.getLogger(MachineWithDrinksDaoImpl.class);
    
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public MachineWithDrinks findById(Integer id) {
        return em.find(MachineWithDrinks.class,  id);
    }

    @Override
    @Transactional(readOnly = true)
    public MachineWithDrinks findFullById(Integer id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<MachineWithDrinks> criteriaQuery
                = criteriaBuilder.createQuery(MachineWithDrinks.class);
        Root<MachineWithDrinks> root
                = criteriaQuery.from(MachineWithDrinks.class);
        root.fetch(MachineWithDrinks_.machineWithDrinksCost,
                JoinType.LEFT);
        criteriaQuery.select(root);
        Predicate predicate = criteriaBuilder.equal(
                root.get(MachineWithDrinks_.id), id);
        criteriaQuery.where(predicate);
        TypedQuery<MachineWithDrinks> typedQuery
                = em.createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    @Override
    public MachineWithDrinks getReference(Integer id) {
        return em.getReference(MachineWithDrinks.class, id);
    }

    @Override
    public List<MachineWithDrinks> findAllFull() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<MachineWithDrinks> criteriaQuery
                = criteriaBuilder.createQuery(MachineWithDrinks.class);
        Root<MachineWithDrinks> root
                = criteriaQuery.from(MachineWithDrinks.class);
        root.fetch(MachineWithDrinks_.machineWithDrinksCost,
                JoinType.LEFT);
        criteriaQuery.select(root);
        TypedQuery<MachineWithDrinks> typedQuery
                = em.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

}
