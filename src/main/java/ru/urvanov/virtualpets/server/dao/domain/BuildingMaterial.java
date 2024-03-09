package ru.urvanov.virtualpets.server.dao.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Запись из справочника материалов для строительства.
 */
@Entity
@Table(name = "building_material")
public class BuildingMaterial implements Serializable {

    private static final long serialVersionUID = -6611026384958159106L;

    /**
     * Первичный ключ. Новые записи в справочник материалов для строительства
     * добавляются скриптами liquibase, первичный ключ не генерируется ни в БД,
     * ни в Java-коде.
     */
    @Id
    private Integer id;

    /**
     * Натуральный ключ. Код материала.
     */
    @Column(name = "code")
    @Enumerated(value = EnumType.STRING)
    private BuildingMaterialType code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BuildingMaterialType getCode() {
        return code;
    }

    public void setCode(BuildingMaterialType code) {
        this.code = code;
    }


    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BuildingMaterial other = (BuildingMaterial) obj;
        return code == other.code;
    }

    @Override
    public String toString() {
        return "BuildingMaterial [id=" + id + ", code=" + code +  "]";
    }

    
}
