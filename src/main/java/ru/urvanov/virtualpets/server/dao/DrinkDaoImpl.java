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
import ru.urvanov.virtualpets.server.dao.domain.Drink;
import ru.urvanov.virtualpets.server.dao.domain.DrinkId;
import ru.urvanov.virtualpets.server.dao.domain.Drink_;

@Repository(value="drinkDao")
public class DrinkDaoImpl implements DrinkDao {

    @PersistenceContext
    private EntityManager em;
    
    @Transactional(readOnly=true)
    @Override
    public Drink findById(DrinkId id) {
        return em.find(Drink.class, id);
    }

    @Override
    public Drink getReference(DrinkId id) {
        return em.getReference(Drink.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Drink> findAllOrderByMachineWithDrinksLevelAndMachineWithDrinksOrder() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Drink> criteriaQuery = cb.createQuery(Drink.class);
        
        Root<Drink> rootDrink = criteriaQuery.from(Drink.class);
        criteriaQuery.select(rootDrink);
        criteriaQuery.orderBy(
                cb.asc(rootDrink.get(Drink_.machineWithDrinksLevel)),
                cb.asc(rootDrink.get(Drink_.machineWithDrinksOrder)));
        TypedQuery<Drink> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

}
