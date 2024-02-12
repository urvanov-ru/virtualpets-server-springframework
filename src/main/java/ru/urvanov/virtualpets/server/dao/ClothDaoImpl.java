/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Cloth;
import ru.urvanov.virtualpets.server.dao.domain.Cloth_;

/**
 * @author fedya
 * 
 */
@Repository(value = "clothDao")
@Transactional
public class ClothDaoImpl implements ClothDao {

    @PersistenceContext
    private EntityManager em;

    /*
     * (non-Javadoc)
     * 
     * @see
     * ru.urvanov.virtualpets.server.dao.ClothDao#findById(java.lang.Integer
     * )
     */
    @Override
    @Transactional(readOnly= true)
    public Cloth findById(Integer id) {
        return em.find(Cloth.class, id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ru.urvanov.virtualpets.server.dao.ClothDao#getReference(java.lang
     * .Integer)
     */
    @Override
    public Cloth getReference(Integer id) {
        return em.getReference(Cloth.class, id);
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
    public Integer getCount() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery = cb.createQuery();
        
        Root<Cloth> rootCloth = criteriaQuery.from(Cloth.class);
        criteriaQuery.select(cb.count(rootCloth.get(Cloth_.id)));
        Query query = em.createQuery(criteriaQuery);
        return ((Long)query.getSingleResult()).intValue();
    }

}
