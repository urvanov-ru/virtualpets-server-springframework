package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Achievement;
import ru.urvanov.virtualpets.server.dao.domain.AchievementCode;
import ru.urvanov.virtualpets.server.dao.domain.Achievement_;

@Repository("achievementDao")
@Transactional
public class AchievementDaoImpl implements AchievementDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Achievement findById(Integer id) {
        return em.find(Achievement.class, id);
    }

    @Override
    public Achievement findByCode(AchievementCode code) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Achievement> criteriaQuery = criteriaBuilder
                .createQuery(Achievement.class);
        Root<Achievement> rootAchievement = criteriaQuery
                .from(Achievement.class);
        criteriaQuery.where(criteriaBuilder.equal(
                rootAchievement.get(Achievement_.code), code));
        TypedQuery<Achievement> typedQuery = em.createQuery(criteriaQuery);
        List<Achievement> lst = typedQuery.getResultList();
        if (lst.size() >= 0) {
            return lst.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Achievement> findAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Achievement> criteriaQuery = criteriaBuilder
                .createQuery(Achievement.class);
        criteriaQuery.from(Achievement.class);
        TypedQuery<Achievement> typedQuery = em.createQuery(criteriaQuery);
        return typedQuery.getResultList();

    }

}
