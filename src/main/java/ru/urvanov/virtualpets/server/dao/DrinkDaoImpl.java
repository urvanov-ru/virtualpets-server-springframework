package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import ru.urvanov.virtualpets.server.dao.domain.Drink;
import ru.urvanov.virtualpets.server.dao.domain.DrinkId;

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
        TypedQuery<Drink> namedQuery = em.createNamedQuery(
                "Drink.findAllOrderByMachineWithDrinksLevelAndMachineWithDrinksOrder",
                Drink.class);
        return namedQuery.getResultList();
    }

}
