/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import ru.urvanov.virtualpets.server.dao.domain.Chat;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fedya
 * 
 */
@Repository(value = "chatDao")
@Transactional
public class ChatDaoImpl implements ChatDao {

    @PersistenceContext
    private EntityManager em;

    /*
     * (non-Javadoc)
     * 
     * @see
     * ru.urvanov.virtualpets.server.dao.ChatDao#findById(java.lang.Integer)
     */
    @Override
    @Transactional(readOnly = true)
    public Chat findById(Integer id) {
        return em.find(Chat.class, id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ru.urvanov.virtualpets.server.dao.ChatDao#findLast(java.lang.Integer,
     * java.lang.Integer)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Chat> findLast(Integer count, Integer userId) {
        jakarta.persistence.Query query = em.createNamedQuery("findLast");
        query.setParameter("userId", userId);
        query.setMaxResults(count);
        @SuppressWarnings("unchecked")
        List<Chat> r = (List<Chat>) query.getResultList();
        // В списке r значение в обратном порядке. Разворачиваем их.
        List<Chat> result = new ArrayList<Chat>(r.size());
        for (int n = r.size() - 1; n >= 0; n--) {
            Chat c = r.get(n);
            result.add(c);
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ru.urvanov.virtualpets.server.dao.ChatDao#save(ru.urvanov.virtualpets
     * .server.domain.Chat)
     */
    @Override
    public void save(Chat chat) {
        if (chat.getId() == null) {
            em.persist(chat);
        } else {
            em.merge(chat);
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

    @SuppressWarnings("unchecked")
    @Override
    public List<Chat> findFromId(Integer fromId, Integer userId) {
        Query query = em.createNamedQuery("findFromId");
        query.setParameter("fromId", fromId);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

}
