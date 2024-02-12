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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks;
import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks_;

/**
 * @author fedya
 *
 */
@Repository(value="machineWithDrinksDao")
@Transactional
public class MachineWithDrinksDaoImpl implements MachineWithDrinksDao {
    
    private static final Logger log = LoggerFactory.getLogger(MachineWithDrinksDaoImpl.class);
    
    @PersistenceContext
    private EntityManager em;

    /* (non-Javadoc)
     * @see ru.urvanov.virtualpets.server.dao.DrinkDao#findById(java.lang.Integer)
     */
    @Override
    public MachineWithDrinks findById(Integer id) {
        return em.find(MachineWithDrinks.class,  id);
    }

    /* (non-Javadoc)
     * @see ru.urvanov.virtualpets.server.dao.DrinkDao#findFullById(java.lang.Integer)
     */
    @Override
    public MachineWithDrinks findFullById(Integer id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<MachineWithDrinks> criteriaQuery = criteriaBuilder.createQuery(MachineWithDrinks.class);
        Root<MachineWithDrinks> root = criteriaQuery.from(MachineWithDrinks.class);
        criteriaQuery.select(root);
        Predicate predicate = criteriaBuilder.equal(root.get(MachineWithDrinks_.id), id);
        criteriaQuery.where(predicate);
        TypedQuery<MachineWithDrinks> typedQuery = em.createQuery(criteriaQuery);
        List<MachineWithDrinks> result = typedQuery.getResultList();
        if (result.size() > 0) {
            MachineWithDrinks machineWithDrinks = result.get(0);
            log.debug("drinkCost.size = %n", machineWithDrinks.getMachineWithDrinksCost().size());
            return machineWithDrinks;
        } else {
            return null;
        }
    }

    /**
     * @return the em
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * @param em the em to set
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

}
