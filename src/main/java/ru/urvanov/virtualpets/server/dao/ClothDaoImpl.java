package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ru.urvanov.virtualpets.server.dao.domain.Cloth;
import ru.urvanov.virtualpets.server.dao.domain.Cloth_;

@Repository(value = "clothDao")
public class ClothDaoImpl implements ClothDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly= true)
    public Cloth findById(String id) {
        return em.find(Cloth.class, id);
    }

    @Override
    public Cloth getReference(String id) {
        return em.getReference(Cloth.class, id);
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getCount() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery = cb.createQuery();
        
        Root<Cloth> rootCloth = criteriaQuery.from(Cloth.class);
        criteriaQuery.select(cb.count(rootCloth.get(Cloth_.id)));
        Query query = em.createQuery(criteriaQuery);
        return ((Long)query.getSingleResult()).intValue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cloth> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cloth> criteriaQuery = cb.createQuery(Cloth.class);
        
        Root<Cloth> rootCloth = criteriaQuery.from(Cloth.class);
        criteriaQuery.select(rootCloth);
        TypedQuery<Cloth> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

}
