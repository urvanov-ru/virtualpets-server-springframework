/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.dao.domain.User_;
/**
 * @author fedya
 *
 */
@Repository(value="userDao")
@Transactional
public class UserDaoImpl implements UserDao {
    
    @PersistenceContext
    private EntityManager em;

    public void save(User user) {
        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
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
    public User findByLogin(String login) {
        Query query = em.createNamedQuery("findByLogin");
        query.setParameter("login", login);
        @SuppressWarnings("unchecked")
        List<User> lst = query.getResultList();
        if (lst.size() == 0) {
            return null;
        } else {
            return lst.get(0);
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<User> list() {
        Query query = em.createNamedQuery("list");
        return query.getResultList();
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        Query query = em.createNamedQuery("findByLoginAndPassword");
        query.setParameter("login", login);
        query.setParameter("password", password);
        return (User) query.getSingleResult();
    }

    @Override
    public User findById(Integer id) {
        return em.find(User.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findOnline() {
        Query query = em.createNamedQuery("findOnline");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -5);
        query.setParameter("date", calendar.getTime());
        return query.getResultList();
    }

    @Override
    public User findByLoginAndEmail(String login, String email) {
        Query query = em.createNamedQuery("findByLoginAndEmail");
        query.setParameter("login", login);
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }

    @Override
    public User findByFacebookKey(String facebookKey) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        Predicate predicate = cb.equal(root.get(User_.facebookKey), facebookKey);
        criteriaQuery.where(predicate);
        List<User> lst = em.createQuery(criteriaQuery).getResultList();
        if (lst.size() == 0) {
            return null;
        } else {
            return lst.get(0);
        }
    }

    @Override
    public User findByUnid(String unid) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        Predicate predicate = cb.equal(root.get(User_.unid), unid);
        criteriaQuery.where(predicate);
        List<User> lst = em.createQuery(criteriaQuery).getResultList();
        if (lst.size() == 0) {
            return null;
        } else {
            return lst.get(0);
        }
    }

    @Override
    public User findByRecoverPasswordKey(String recoverKey) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        Predicate predicate1 = cb.equal(root.get(User_.recoverPasswordKey), recoverKey);
        Predicate predicate2 = cb.lessThan(root.get(User_.recoverPasswordValid), new Date());
        Predicate predicate = cb.and(predicate1, predicate2);
        criteriaQuery.where(predicate);
        List<User> lst = em.createQuery(criteriaQuery).getResultList();
        if (lst.size() == 0) {
            return null;
        } else {
            return lst.get(0);
        }
    }

    @Override
    public User findByVkontakteKey(String vkontakteKey) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        Predicate predicate = cb.equal(root.get(User_.vkontakteKey), vkontakteKey);
        criteriaQuery.where(predicate);
        List<User> lst = em.createQuery(criteriaQuery).getResultList();
        if (lst.size() == 0) {
            return null;
        } else {
            return lst.get(0);
        }
    }

    @Override
    public User findByTwitterKey(String twitterKey) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        Predicate predicate = cb.equal(root.get(User_.twitterKey), twitterKey);
        criteriaQuery.where(predicate);
        List<User> lst = em.createQuery(criteriaQuery).getResultList();
        if (lst.size() == 0) {
            return null;
        } else {
            return lst.get(0);
        }
        
    }

    @Override
    public List<User> findLastRegisteredUsers(int start, int limit) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        Order order = cb.desc(root.get(User_.registrationDate));
        criteriaQuery.orderBy(order);
        TypedQuery<User> typedQuery = em.createQuery(criteriaQuery);
        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(limit);
        return typedQuery.getResultList();
    }

    @Override
    public User getReference(Integer id) {
        return this.em.getReference(User.class, id);
    }
}
