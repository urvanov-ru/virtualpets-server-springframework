/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterial_;

@Repository(value="buildingMaterialDao")
public class BuildingMaterialDaoImpl implements BuildingMaterialDao {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    @Transactional(readOnly = true)
    public BuildingMaterial findById(BuildingMaterialId id) {
        return em.find(BuildingMaterial.class, id);
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional(readOnly = true)
    public BuildingMaterial findByCode(BuildingMaterialId code) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<BuildingMaterial> criteriaQuery = criteriaBuilder.createQuery(BuildingMaterial.class);
        Root<BuildingMaterial> rootBuildingMaterial = criteriaQuery.from(BuildingMaterial.class);
        criteriaQuery.select(rootBuildingMaterial);
        Predicate predicate = criteriaBuilder.equal(rootBuildingMaterial.get(BuildingMaterial_.id), code);
        criteriaQuery.where(predicate);
        TypedQuery<BuildingMaterial> query = em.createQuery(criteriaQuery);
        List<BuildingMaterial> lst = query.getResultList();
        if (lst.size() >= 1) {
            return lst.get(0);
        } else {
            return null;
        }
    }

    @Override
    public BuildingMaterial getReference(
            BuildingMaterialId id) {
        return em.getReference(BuildingMaterial.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BuildingMaterial> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BuildingMaterial> criteriaQuery = cb.createQuery(BuildingMaterial.class);
        
        Root<BuildingMaterial> rootBuildingMaterial = criteriaQuery.from(BuildingMaterial.class);
        criteriaQuery.select(rootBuildingMaterial);
        TypedQuery<BuildingMaterial> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

}
