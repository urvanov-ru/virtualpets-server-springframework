package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ru.urvanov.virtualpets.server.dao.domain.Room;
import ru.urvanov.virtualpets.server.dao.domain.Room_;

@Repository(value="roomDao")
public class RoomDaoImpl implements RoomDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public Room findByPetId(Integer petId) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder(); // (1)
        CriteriaQuery<Room> criteriaQuery
                = criteriaBuilder.createQuery(Room.class); // (2)
        Root<Room> rootRoom = criteriaQuery.from(Room.class); // (3)
        criteriaQuery.select(rootRoom); // (4)
        Predicate predicate = criteriaBuilder.equal(
                rootRoom.get(Room_.petId),
                petId); // (5)
        criteriaQuery.where(predicate); // (6)
        TypedQuery<Room> query = em.createQuery(criteriaQuery); // (7)
        return query.getSingleResult(); // (8)
    }
    
    @Override
    @Transactional(readOnly = true)
    public Room findByPetIdOrNull(Integer petId) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Room> criteriaQuery
                = criteriaBuilder.createQuery(Room.class);
        Root<Room> rootRoom = criteriaQuery.from(Room.class);
        criteriaQuery.select(rootRoom);
        Predicate predicate = criteriaBuilder.equal(
                rootRoom.get(Room_.petId),
                petId);
        criteriaQuery.where(predicate);
        TypedQuery<Room> query = em.createQuery(criteriaQuery);
        List<Room> rooms = query.getResultList();
        if (rooms.size() == 1) {
            return rooms.get(0);
        } else if (rooms.size() > 1) {
            throw new NonUniqueResultException();
        } else {
            return null;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public long existsByPetId(Integer petId) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder(); // (1)
        CriteriaQuery<Long> criteriaQuery
                = criteriaBuilder.createQuery(Long.class);  // (2)
        Root<Room> rootRoom = criteriaQuery.from(Room.class);  // (3)
        Expression<Long> count = criteriaBuilder
                .count(rootRoom.get(Room_.petId));  // (4)
        criteriaQuery.select(count);  // (5)
        Predicate predicate = criteriaBuilder.equal(
                rootRoom.get(Room_.petId),
                petId);  // (6)
        criteriaQuery.where(predicate); // (7)
        TypedQuery<Long> query = em.createQuery(criteriaQuery); // (8)
        return query.getSingleResult(); // (9)
    }

    @Override
    @Transactional
    public void save(Room room) {
        em.merge(room);
    }

    @Override
    @Transactional
    public void delete(Room room) {
        em.remove(room);
    }
}
