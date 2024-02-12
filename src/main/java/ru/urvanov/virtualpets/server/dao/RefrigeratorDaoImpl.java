/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Map;

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

import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;
import ru.urvanov.virtualpets.server.dao.domain.RefrigeratorCost;
import ru.urvanov.virtualpets.server.dao.domain.Refrigerator_;

/**
 * @author fedya
 * 
 */
@Repository(value = "refrigeratorDao")
@Transactional
public class RefrigeratorDaoImpl implements RefrigeratorDao {

    private static final Logger log = LoggerFactory.getLogger(RefrigeratorDaoImpl.class);
    
    @PersistenceContext
    private EntityManager em;

    /**
     * 
     */
    public RefrigeratorDaoImpl() {
        // TODO Auto-generated constructor stub
    }

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
        Predicate predicate = criteriaBuilder.equal(root.get(Refrigerator_.id),
                id);
        criteriaQuery.where(predicate);
        TypedQuery<Refrigerator> query = em.createQuery(criteriaQuery);
        List<Refrigerator> result = query.getResultList();
        if (result.size() >= 1) {
            Refrigerator refrigerator = result.get(0);
            Map<BuildingMaterial, RefrigeratorCost> refrigeratorCost = refrigerator.getRefrigeratorCost();
            log.debug("refrigeratorCost size = %n", refrigeratorCost.size());
            return refrigerator;
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
     * @param em
     *            the em to set
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

}
