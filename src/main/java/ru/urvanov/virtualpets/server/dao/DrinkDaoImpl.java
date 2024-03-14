package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Cloth;
import ru.urvanov.virtualpets.server.dao.domain.Drink;
import ru.urvanov.virtualpets.server.dao.domain.DrinkType;
import ru.urvanov.virtualpets.server.dao.domain.Drink_;

@Repository(value="drinkDao")
public class DrinkDaoImpl implements DrinkDao {

    @PersistenceContext
    private EntityManager em;
    
    @Transactional(readOnly=true)
    @Override
    public Drink findById(DrinkType id) {
        return em.find(Drink.class, id);
    }

    @Transactional(readOnly=true)
    @Override
    public Drink getReference(Integer id) {
        return em.getReference(Drink.class, id);
    }

    @Transactional(readOnly=true)
    @Override
    public Drink findByCode(DrinkType code) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Drink> criteriaQuery = cb.createQuery(Drink.class);
        Root<Drink> root = criteriaQuery.from(Drink.class);
        criteriaQuery.select(root);
        criteriaQuery.where(cb.equal(root.get(Drink_.id), code));
        TypedQuery<Drink> query = em.createQuery(criteriaQuery);
        List<Drink> result = query.getResultList();
        if (result.size() >= 1) {
            return result.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Drink getReference(DrinkType id) {
        return em.getReference(Drink.class, id);
    }

    @Override
    public List<Drink> findAllOrderByMachineWithDrinksLevelAndMachineWithDrinksOrder() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Drink> criteriaQuery = cb.createQuery(Drink.class);
        
        Root<Drink> rootDrink = criteriaQuery.from(Drink.class);
        criteriaQuery.select(rootDrink);
        criteriaQuery.orderBy(cb.asc(rootDrink.get(Drink_.machineWithDrinksLevel)), cb.asc(rootDrink.get(Drink_.machineWithDrinksOrder)));
        TypedQuery<Drink> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

}
