package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import ru.urvanov.virtualpets.server.dao.domain.Level;

@Repository("levelDao")
public class LevelDaoImpl implements LevelDao {

    @PersistenceContext
    private EntityManager em;
    
    @Transactional(readOnly = true)
    @Override
    public Level findById(Integer id) {
        return em.find(Level.class, id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Level> findAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Level> criteriaQuery
                = criteriaBuilder.createQuery(Level.class);
        criteriaQuery.from(Level.class);
        TypedQuery<Level> typedQuery = em.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

   
}
