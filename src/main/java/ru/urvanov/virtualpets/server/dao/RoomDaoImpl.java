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

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Room;
import ru.urvanov.virtualpets.server.dao.domain.Room_;

/**
 * @author fedya
 *
 */
@Repository(value="roomDao")
@Transactional
public class RoomDaoImpl implements RoomDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Room findByPetId(Integer petId) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Room> criteriaQuery = criteriaBuilder.createQuery(Room.class);
        Root<Room> rootRoom = criteriaQuery.from(Room.class);
        criteriaQuery.select(rootRoom);
        Predicate predicate = criteriaBuilder.equal(rootRoom.get(Room_.petId), petId);
        criteriaQuery.where(predicate);
        TypedQuery<Room> query = em.createQuery(criteriaQuery);
        List<Room> lst = query.getResultList();
        if (lst.size() >= 1) {
            return lst.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void save(Room room) {
        Room existRoom = findByPetId(room.getPetId());
        if (existRoom == null) {
            em.persist(room);
        } else {
            em.merge(room);
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
