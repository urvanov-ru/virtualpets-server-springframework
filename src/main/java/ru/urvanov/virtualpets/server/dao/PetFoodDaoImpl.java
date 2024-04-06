package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ru.urvanov.virtualpets.server.dao.domain.FoodId;
import ru.urvanov.virtualpets.server.dao.domain.Food_;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetFood;
import ru.urvanov.virtualpets.server.dao.domain.PetFood_;
import ru.urvanov.virtualpets.server.dao.domain.Pet_;

@Repository(value = "petFoodDao")
public class PetFoodDaoImpl implements PetFoodDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly=true)
    public PetFood findById(Integer id) {
        return em.find(PetFood.class, id);
    }

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
    
    @Override
    @Transactional(readOnly=true)
    public List<PetFood> findFullByPetId(Integer petId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PetFood> criteriaQuery = cb.createQuery(PetFood.class);
        Root<PetFood> petFoodRoot = criteriaQuery.from(PetFood.class);
        petFoodRoot.fetch(PetFood_.food, JoinType.LEFT);
        Predicate predicate = cb.equal(petFoodRoot.get(PetFood_.pet).get(Pet_.id),
                petId);
        criteriaQuery.where(predicate);
        return em.createQuery(criteriaQuery).getResultList();
    }

    @Override
    @Transactional(readOnly=true)
    public List<PetFood> findByPet(Pet pet) {
        return findByPetId(pet.getId());
    }

    @Override
    @Transactional
    public void save(PetFood food) {
        if (food.getId() == null) {
            em.persist(food);
        } else {
            em.merge(food);
        }
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional(readOnly = true)
    public PetFood findByPetIdAndFoodType(Integer petId, FoodId foodType) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PetFood> criteriaQuery = cb.createQuery(PetFood.class);
        Root<PetFood> petFoodRoot = criteriaQuery.from(PetFood.class);
        criteriaQuery.select(petFoodRoot).distinct(true);
        
        Predicate predicatePetId = cb.equal(petFoodRoot.get(PetFood_.pet).get(Pet_.id), petId);
        Predicate predicateFoodType = cb.equal(petFoodRoot.get(PetFood_.food).get(Food_.id), foodType);
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
