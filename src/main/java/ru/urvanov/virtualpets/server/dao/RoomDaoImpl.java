package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
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
    public Optional<Room> findByPetId(Integer petId) {
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
        List<Room> rooms = query.getResultList(); // (8)
        return DataAccessUtils.optionalResult(rooms); // (9)
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
