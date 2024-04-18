package ru.urvanov.virtualpets.server.dao;

import java.util.List;

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
    public long countByPetIdAndJournalEntryCode(Integer petId,
            JournalEntryId code) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder
                .createQuery(Long.class);
        Root<PetJournalEntry> rootPetJournalEntry = criteriaQuery
                .from(PetJournalEntry.class);
        Predicate journalEntryCodeEqual = criteriaBuilder.equal(
                rootPetJournalEntry.get(PetJournalEntry_.journalEntry),
                code);
        Predicate petIdEqual = criteriaBuilder.equal(
                rootPetJournalEntry.get(PetJournalEntry_.pet)
                        .get(Pet_.id),
                petId);
        Predicate andWhere = criteriaBuilder.and(
                journalEntryCodeEqual,
                petIdEqual);
        criteriaQuery.where(andWhere);
        Expression<Long> count = criteriaBuilder.count(rootPetJournalEntry);
        criteriaQuery.select(count);
        TypedQuery<Long> typedQuery
                = em.createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
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
