package ru.urvanov.virtualpets.server.dao;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.dao.domain.User_;

@Repository(value="userDao")
public class UserDaoImpl implements UserDao {
    
    @Autowired
    private Clock clock;
    
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(User user) {
        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public User findByLoginAndPassword(String login, String password) {
        Query query = em.createNamedQuery("findByLoginAndPassword");
        query.setParameter("login", login);
        query.setParameter("password", password);
        return (User) query.getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Integer id) {
        return em.find(User.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<User> findOnline() {
        Query query = em.createNamedQuery("findOnline");
        OffsetDateTime offsetDateTime = OffsetDateTime.now(clock);
        offsetDateTime = offsetDateTime.minusMinutes(5);
        query.setParameter("date", offsetDateTime);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByLoginAndEmail(String login, String email) {
        Query query = em.createNamedQuery("findByLoginAndEmail");
        query.setParameter("login", login);
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public User findByRecoverPasswordKey(String recoverKey) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        Predicate predicate1 = cb.equal(root.get(User_.recoverPasswordKey), recoverKey);
        Predicate predicate2 = cb.lessThan(root.get(User_.recoverPasswordValid), OffsetDateTime.now(clock));
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
    @Transactional(readOnly = true)
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
