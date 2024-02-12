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

import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialType;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterial_;

/**
 * @author fedya
 *
 */
@Repository(value="buildingMaterialDao")
public class BuildingMaterialDaoImpl implements BuildingMaterialDao {

    @PersistenceContext
    private EntityManager em;
    
    /* (non-Javadoc)
     * @see ru.urvanov.virtualpets.server.dao.BuildingMaterialDao#findById(java.lang.Integer)
     */
    @Override
    public BuildingMaterial findById(Integer id) {
        return em.find(BuildingMaterial.class, id);
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

    @Override
    public BuildingMaterial findByCode(BuildingMaterialType code) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<BuildingMaterial> criteriaQuery = criteriaBuilder.createQuery(BuildingMaterial.class);
        Root<BuildingMaterial> rootBuildingMaterial = criteriaQuery.from(BuildingMaterial.class);
        criteriaQuery.select(rootBuildingMaterial);
        Predicate predicate = criteriaBuilder.equal(rootBuildingMaterial.get(BuildingMaterial_.code), code);
        criteriaQuery.where(predicate);
        TypedQuery<BuildingMaterial> query = em.createQuery(criteriaQuery);
        List<BuildingMaterial> lst = query.getResultList();
        if (lst.size() >= 1) {
            return lst.get(0);
        } else {
            return null;
        }
    }

}
