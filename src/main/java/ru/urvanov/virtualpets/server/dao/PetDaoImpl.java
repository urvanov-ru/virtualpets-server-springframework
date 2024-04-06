package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.MapJoin;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryId;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry;
import ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry_;
import ru.urvanov.virtualpets.server.dao.domain.Pet_;

@Repository(value = "petDao")
public class PetDaoImpl implements PetDao {

    private Logger logger = LoggerFactory.getLogger(PetDaoImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public Pet findById(Integer id) {
        return em.find(Pet.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Pet findFullById(Integer id) {
        TypedQuery<Pet> query = em.createNamedQuery("findFullById", Pet.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void save(Pet pet) {
        if (pet.getId() == null) {
            em.persist(pet);
        } else {
            em.merge(pet);
        }
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Pet pet = em.find(Pet.class, id);
        em.remove(pet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pet> findByUserId(Integer userId) {
        TypedQuery<Pet> query = em.createNamedQuery("findByUserId", Pet.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public Pet getReference(Integer id) {
        return em.getReference(Pet.class, id);
    }

    @Override
    @Transactional
    public void updatePetsTask() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object> cq = cb.createQuery();
        Root<Pet> rootPet = cq.from(Pet.class);
        cq.select(rootPet);
        Query query = em.createQuery(cq);
        org.hibernate.query.Query hibernateQuery = ((org.hibernate.query.Query) query
                .unwrap(org.hibernate.query.Query.class));
        hibernateQuery.setCacheMode(CacheMode.IGNORE);
        @SuppressWarnings("unchecked")
        ScrollableResults<Pet> sr = hibernateQuery.scroll(ScrollMode.FORWARD_ONLY);
        try {
            while (sr.next()) {
                try {
                    Pet pet = (Pet) sr.get();
                    pet.setMood(decParameter(pet.getMood()));
                    pet.setDrink(decParameter(pet.getDrink()));
                    pet.setSatiety(decParameter(pet.getSatiety()));
                    pet.setEducation(decParameter(pet.getEducation()));
                    em.merge(pet);
                    Session session = em.unwrap(Session.class);
                    session.flush();
                    session.clear();
                } catch (Exception ex) {
                    logger.error("updatePetsTask step failed.", ex);
                }
            }
        } finally {
            sr.close();
        }
    }

    private int decParameter(int mood) {
        if (mood > 0) {
            mood = mood - 1;
        }
        return mood;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getPetNewJournalEntriesCount(Integer petId) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Pet> rootPet = criteriaQuery.from(Pet.class);
        //Fetch<Pet, PetJournalEntry> fetchPetJournalEntries = rootPet.fetch(Pet_.journalEntries);
        MapJoin<Pet, JournalEntryId, PetJournalEntry> joinPetJournalEntries = rootPet.join(Pet_.journalEntries, JoinType.LEFT);
        criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(joinPetJournalEntries.get(PetJournalEntry_.readed), false)),
                criteriaBuilder.equal(rootPet.get(Pet_.id), petId));
        
        criteriaQuery.select(criteriaBuilder.count(joinPetJournalEntries.get(PetJournalEntry_.id)));
        TypedQuery<Long> typedQuery = em.createQuery(criteriaQuery);
        Long count = typedQuery.getSingleResult();
        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pet> findLastCreatedPets(int start, int limit) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Pet> criteriaQuery = criteriaBuilder.createQuery(Pet.class);
        Root<Pet> rootPet = criteriaQuery.from(Pet.class);
        Order orderCreatedDate = criteriaBuilder.desc(rootPet.get(Pet_.createdDate));
        criteriaQuery.orderBy(orderCreatedDate);
        TypedQuery<Pet> typedQuery = em.createQuery(criteriaQuery);
        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(limit);
        return typedQuery.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Pet findByIdWithBuildingMaterials(Integer id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Pet> criteriaQuery = criteriaBuilder.createQuery(Pet.class);
        Root<Pet> rootPet = criteriaQuery.from(Pet.class);
        rootPet.fetch(Pet_.buildingMaterials);
        TypedQuery<Pet> typedQuery = em.createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }
}
