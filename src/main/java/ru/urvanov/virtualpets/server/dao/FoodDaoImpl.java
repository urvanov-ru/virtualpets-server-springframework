/**
 * 
 */
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
import ru.urvanov.virtualpets.server.dao.domain.Food;
import ru.urvanov.virtualpets.server.dao.domain.FoodType;
import ru.urvanov.virtualpets.server.dao.domain.Food_;

/**
 * @author fedya
 * 
 */
@Repository(value = "foodDao")
@Transactional
public class FoodDaoImpl implements FoodDao {

    @PersistenceContext
    private EntityManager em;

    /*
     * (non-Javadoc)
     * 
     * @see
     * ru.urvanov.virtualpets.server.dao.FoodDao#findById(java.lang.Integer)
     */
    @Override
    public Food findById(FoodType id) {
        return em.find(Food.class, id);
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }


    @Override
    public Food getReference(FoodType id) {
        return em.getReference(Food.class, id);
    }

    @Override
    public List<Food> findAllOrderByRefrigeratorLevelAndRefrigeratorOrder() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Food> criteriaQuery = cb.createQuery(Food.class);
        
        Root<Food> rootFood = criteriaQuery.from(Food.class);
        criteriaQuery.select(rootFood);
        criteriaQuery.orderBy(cb.asc(rootFood.get(Food_.refrigeratorLevel)), cb.asc(rootFood.get(Food_.refrigeratorOrder)));
        TypedQuery<Food> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

}
