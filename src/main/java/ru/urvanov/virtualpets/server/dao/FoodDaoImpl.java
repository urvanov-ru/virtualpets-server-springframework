/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public Food findById(Integer id) {
        return em.find(Food.class, id);
    }

    /**
     * @return the em
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * @param em
     *            the em to set
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Food findByCode(FoodType code) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Food> criteriaQuery = criteriaBuilder
                .createQuery(Food.class);
        Root<Food> rootFood = criteriaQuery.from(Food.class);
        criteriaQuery.select(rootFood);
        Predicate predicate = criteriaBuilder.equal(rootFood.get(Food_.code),
                code);
        criteriaQuery.where(predicate);
        TypedQuery<Food> query = em.createQuery(criteriaQuery);
        List<Food> lst = query.getResultList();
        if (lst.size() >= 1) {
            return lst.get(0);
        } else {
            return null;
        }
    }

}
