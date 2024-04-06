package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryId;
import ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry;
import ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry_;
import ru.urvanov.virtualpets.server.dao.domain.Pet_;

@Repository("petJournalEntryDao")
public class PetJournalEntryDaoImpl implements PetJournalEntryDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<PetJournalEntry> findLastByPetId(Integer petId, Integer count) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<PetJournalEntry> criteriaQuery = criteriaBuilder
                .createQuery(PetJournalEntry.class);
        Root<PetJournalEntry> rootPetJournalEntry = criteriaQuery
                .from(PetJournalEntry.class);
        criteriaQuery.where(criteriaBuilder.equal(
                rootPetJournalEntry.get(PetJournalEntry_.pet).get(Pet_.id),
                petId));
        criteriaQuery.orderBy(criteriaBuilder.desc(rootPetJournalEntry
                .get(PetJournalEntry_.createdAt)));
        TypedQuery<PetJournalEntry> typedQuery = em.createQuery(criteriaQuery);
        typedQuery.setMaxResults(count);
        List<PetJournalEntry> result = typedQuery.getResultList();
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public PetJournalEntry findByPetIdAndJournalEntryCode(Integer petId,
            JournalEntryId code) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<PetJournalEntry> criteriaQuery = criteriaBuilder
                .createQuery(PetJournalEntry.class);
        Root<PetJournalEntry> rootPetJournalEntry = criteriaQuery
                .from(PetJournalEntry.class);
        criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(
                rootPetJournalEntry.get(PetJournalEntry_.journalEntry), code), criteriaBuilder.equal(
                rootPetJournalEntry.get(PetJournalEntry_.pet).get(Pet_.id),
                petId)));
        criteriaQuery.select(rootPetJournalEntry);
        TypedQuery<PetJournalEntry> typedQuery = em.createQuery(criteriaQuery);
        List<PetJournalEntry> result = typedQuery.getResultList();
        if (result.size() > 0)
            return result.get(0);
        else
            return null;
    }
    
    @Transactional
    @Override
    public void save(PetJournalEntry petJournalEntry) {
        if (petJournalEntry.getId() == null) {
            em.persist(petJournalEntry);
        } else {
            em.merge(petJournalEntry);
        }
    }

}
