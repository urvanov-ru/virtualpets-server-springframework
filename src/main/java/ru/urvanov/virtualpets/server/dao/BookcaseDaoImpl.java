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

import ru.urvanov.virtualpets.server.dao.domain.Bookcase;
import ru.urvanov.virtualpets.server.dao.domain.Bookcase_;

/**
 * @author fedya
 *
 */
@Repository(value="bookcaseDao")
@Transactional
public class BookcaseDaoImpl implements BookcaseDao {

    private static final Logger log = LoggerFactory.getLogger(BookcaseDaoImpl.class);
    @PersistenceContext
    private EntityManager em;

    /* (non-Javadoc)
     * @see ru.urvanov.virtualpets.server.dao.BookcaseDao#findFullById(java.lang.Integer)
     */
    @Override
    public Bookcase findFullById(Integer id) {
         CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
         CriteriaQuery<Bookcase> criteriaQuery =  criteriaBuilder.createQuery(Bookcase.class);
         Root<Bookcase> root = criteriaQuery.from(Bookcase.class);
         criteriaQuery.select(root);
         Predicate predicate = criteriaBuilder.equal(root.get(Bookcase_.id), id);
         criteriaQuery.where(predicate);
         TypedQuery<Bookcase> typedQuery = em.createQuery(criteriaQuery);
         List<Bookcase> result = typedQuery.getResultList();
         if (result.size() > 0) {
             Bookcase bookcase = result.get(0);
             log.debug("bookcaseCost size %n", bookcase.getBookcaseCost().size());
             return bookcase;
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

    @Override
    public Bookcase findById(Integer id) {
        return em.find(Bookcase.class, id);
    }

}
