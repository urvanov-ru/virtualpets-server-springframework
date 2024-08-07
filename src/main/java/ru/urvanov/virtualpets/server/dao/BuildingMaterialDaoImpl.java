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
    public Optional<BuildingMaterial> findById(BuildingMaterialId id) {
        BuildingMaterial buildingMaterial = em.find(BuildingMaterial.class, id);
        return Optional.ofNullable(buildingMaterial);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BuildingMaterial> findByCode(BuildingMaterialId code) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<BuildingMaterial> criteriaQuery
                = criteriaBuilder.createQuery(BuildingMaterial.class);
        Root<BuildingMaterial> rootBuildingMaterial = criteriaQuery
                .from(BuildingMaterial.class);
        criteriaQuery.select(rootBuildingMaterial);
        Predicate predicate = criteriaBuilder.equal(
                rootBuildingMaterial.get(BuildingMaterial_.id), code);
        criteriaQuery.where(predicate);
        TypedQuery<BuildingMaterial> query
                = em.createQuery(criteriaQuery);
        List<BuildingMaterial> buildingMaterials = query.getResultList();
        return DataAccessUtils.optionalResult(buildingMaterials);
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
        CriteriaQuery<BuildingMaterial> criteriaQuery
                = cb.createQuery(BuildingMaterial.class);
        Root<BuildingMaterial> rootBuildingMaterial
                = criteriaQuery.from(BuildingMaterial.class);
        criteriaQuery.select(rootBuildingMaterial);
        TypedQuery<BuildingMaterial> query
                = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

}
