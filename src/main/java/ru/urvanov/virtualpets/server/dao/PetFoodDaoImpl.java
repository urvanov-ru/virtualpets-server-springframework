/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.FoodType;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetFood;
import ru.urvanov.virtualpets.server.dao.domain.Food_;
import ru.urvanov.virtualpets.server.dao.domain.PetFood_;
import ru.urvanov.virtualpets.server.dao.domain.Pet_;

/**
 * @author fedya
 * 
 */
@Repository(value = "petFoodDao")
@Transactional
public class PetFoodDaoImpl implements PetFoodDao {

    @PersistenceContext
    private EntityManager em;

    /*
     * (non-Javadoc)
     * 
     * @see
     * ru.urvanov.virtualpets.server.dao.FoodDao#findById(java.lang.Integer)
     */
    @Override
    @Transactional(readOnly=true)
    public PetFood findById(Integer id) {
        return em.find(PetFood.class, id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ru.urvanov.virtualpets.server.dao.FoodDao#findByPetId(java.lang.Integer
     * )
     */
    @Override
    @Transactional(readOnly=true)
    public List<PetFood> findByPetId(Integer petId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PetFood> criteriaQuery = cb.createQuery(PetFood.class);
        Root<PetFood> petFoodRoot = criteriaQuery.from(PetFood.class);
        criteriaQuery.select(petFoodRoot).distinct(true);
        Predicate predicate = cb.equal(petFoodRoot.get(PetFood_.pet).get(Pet_.id),
                petId);
        criteriaQuery.where(predicate);
        return em.createQuery(criteriaQuery).getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ru.urvanov.virtualpets.server.dao.FoodDao#findByPet(ru.urvanov
     * .virtualpets.server.domain.Pet)
     */
    @Override
    @Transactional(readOnly=true)
    public List<PetFood> findByPet(Pet pet) {
        return findByPetId(pet.getId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ru.urvanov.virtualpets.server.dao.FoodDao#save(ru.urvanov.virtualpets
     * .server.domain.Food)
     */
    @Override
    @Transactional
    public void save(PetFood food) {
        if (food.getId() == null) {
            em.persist(food);
        } else {
            em.merge(food);
        }
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
    public PetFood findByPetIdAndFoodType(Integer petId, FoodType foodType) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PetFood> criteriaQuery = cb.createQuery(PetFood.class);
        Root<PetFood> petFoodRoot = criteriaQuery.from(PetFood.class);
        criteriaQuery.select(petFoodRoot).distinct(true);
        
        Predicate predicatePetId = cb.equal(petFoodRoot.get(PetFood_.pet).get(Pet_.id), petId);
        Predicate predicateFoodType = cb.equal(petFoodRoot.get(PetFood_.food).get(Food_.code), foodType);
        Predicate predicate = cb.and(predicatePetId, predicateFoodType);
        criteriaQuery.where(predicate);
        List<PetFood> foods = em.createQuery(criteriaQuery).getResultList();
        if (foods.size() >= 1) {
            return foods.get(0);
        } else {
            return null;
        }
        
    }

}
