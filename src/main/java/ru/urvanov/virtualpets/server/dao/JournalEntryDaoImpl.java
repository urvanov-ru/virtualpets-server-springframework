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

import ru.urvanov.virtualpets.server.dao.domain.JournalEntry;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryType;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntry_;

@Repository("journalEntryDao")
public class JournalEntryDaoImpl implements JournalEntryDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public JournalEntry findById(Integer id) {
        return em.find(JournalEntry.class, id);
    }

    @Transactional(readOnly = true)
    @Override
    public JournalEntry findByCode(JournalEntryType code) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<JournalEntry> criteriaQuery = cb.createQuery(JournalEntry.class);
        Root<JournalEntry> rootJournalEntry = criteriaQuery.from(JournalEntry.class);
        Predicate predicate = cb.equal(rootJournalEntry.get(JournalEntry_.code), code);
        criteriaQuery.where(predicate);
        criteriaQuery.select(rootJournalEntry);
        TypedQuery<JournalEntry> typedQuery = em.createQuery(criteriaQuery);
        List<JournalEntry> result = typedQuery.getResultList();
        if (result.size() >= 1) {
            return result.get(0);
        } else {
            return null;
        }
    }
    
    @Override
    public JournalEntry getReference(Integer id) {
        return em.getReference(JournalEntry.class, id);
    }
    
}
